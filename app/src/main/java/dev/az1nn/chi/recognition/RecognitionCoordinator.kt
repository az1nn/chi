package dev.az1nn.chi.recognition

import dev.az1nn.chi.ink.InkStroke

/**
 * Asynchronous, vendor-independent recognition seam.
 * Adapters must process handwriting locally and never upload strokes or candidate text.
 * Model installation/readiness and vendor choice belong to T013/T014, not this contract.
 */
fun interface RecognitionCancellation {
    fun cancel()
}

interface InkRecognizer {
    fun recognize(
        strokes: List<InkStroke>,
        onCompleted: (RecognitionOutcome) -> Unit
    ): RecognitionCancellation
}

sealed interface RecognitionOutcome {
    data class Candidates(val texts: List<String>) : RecognitionOutcome
    data object ModelUnavailable : RecognitionOutcome
    data object Failed : RecognitionOutcome
}

sealed interface RecognitionState {
    data object Idle : RecognitionState
    data object Processing : RecognitionState
    data class Ready(val choices: List<String>, val editorEpoch: Long) : RecognitionState
    data object ModelUnavailable : RecognitionState
    data object Failed : RecognitionState
}

/**
 * Holds only transient ink snapshots and recognition candidates.
 *
 * Every new submission, editor change, ink change or keyboard shutdown must invalidate
 * previous work. A completion from a canceled adapter is ignored even if that adapter
 * continues to call back. Committing requires explicit selection, an unchanged editor
 * epoch and EditorGateway's own fresh active-InputConnection check.
 *
 * State methods are synchronized because recognizer callbacks can arrive off the UI thread.
 * A future Android UI observer must marshal rendering to the IME main thread.
 */
class RecognitionCoordinator(private val recognizer: InkRecognizer) {
    private val lock = Any()
    private var requestId = 0L
    private var pending: RecognitionCancellation? = null
    private var current: RecognitionState = RecognitionState.Idle

    val state: RecognitionState get() = synchronized(lock) { current }

    /**
     * Starts only with an eligible editor and at least one complete, nonempty stroke.
     * No vendor recognizer or automatic text insertion is activated by this seam.
     */
    fun submit(strokes: List<InkStroke>, editorEpoch: Long?): Boolean {
        if (editorEpoch == null || strokes.isEmpty() || strokes.any { it.points.isEmpty() }) {
            invalidate()
            return false
        }

        val snapshot = strokes.map { InkStroke(it.points.toList()) }
        val previous: RecognitionCancellation?
        val id: Long
        synchronized(lock) {
            requestId++
            id = requestId
            previous = pending
            pending = null
            current = RecognitionState.Processing
        }
        previous?.cancel()

        // Cancellation itself can synchronously trigger a lifecycle invalidation.
        if (synchronized(lock) { id != requestId }) return false

        val handle = try {
            recognizer.recognize(snapshot) { result ->
                receive(id, editorEpoch, result)
            }
        } catch (_: RuntimeException) {
            receive(id, editorEpoch, RecognitionOutcome.Failed)
            return true
        }

        val obsolete = synchronized(lock) {
            if (id != requestId) {
                true
            } else {
                if (current == RecognitionState.Processing) pending = handle
                false
            }
        }
        if (obsolete) handle.cancel()
        return true
    }

    /** Invoke for every ink edit, field focus change, IME hide/destroy or privacy transition. */
    fun invalidate() {
        val handle: RecognitionCancellation?
        synchronized(lock) {
            requestId++
            handle = pending
            pending = null
            current = RecognitionState.Idle
        }
        handle?.cancel()
    }

    /**
     * Single-use explicit candidate action: consumes first to prevent double taps.
     * The supplied commit function must delegate to EditorGateway.commitCandidate,
     * which independently checks the live focus-generation token.
     */
    fun acceptCandidate(
        index: Int,
        currentEditorEpoch: Long?,
        commit: (candidate: String, editorEpoch: Long) -> Boolean
    ): Boolean {
        val selected: Pair<String, Long> = synchronized(lock) {
            val ready = current as? RecognitionState.Ready ?: return false
            if (currentEditorEpoch != ready.editorEpoch) {
                requestId++
                current = RecognitionState.Idle
                return false
            }
            if (index !in ready.choices.indices) return false
            requestId++
            current = RecognitionState.Idle
            ready.choices[index] to ready.editorEpoch
        }
        return commit(selected.first, selected.second)
    }

    private fun receive(id: Long, epoch: Long, result: RecognitionOutcome) {
        synchronized(lock) {
            if (id != requestId || current != RecognitionState.Processing) return
            pending = null
            current = when (result) {
                is RecognitionOutcome.Candidates -> {
                    val choices = result.texts
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                        .distinct()
                        .take(5)
                    if (choices.isEmpty()) RecognitionState.Failed
                    else RecognitionState.Ready(choices, epoch)
                }
                RecognitionOutcome.ModelUnavailable -> RecognitionState.ModelUnavailable
                RecognitionOutcome.Failed -> RecognitionState.Failed
            }
        }
    }
}
