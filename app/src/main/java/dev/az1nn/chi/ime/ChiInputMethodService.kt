package dev.az1nn.chi.ime

import android.graphics.Color
import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import dev.az1nn.chi.R
import dev.az1nn.chi.ink.InkCanvasView
import dev.az1nn.chi.ink.InkSession
import dev.az1nn.chi.ink.InkStroke
import dev.az1nn.chi.recognition.InkRecognizer
import dev.az1nn.chi.recognition.RecognitionCancellation
import dev.az1nn.chi.recognition.RecognitionCoordinator
import dev.az1nn.chi.recognition.RecognitionOutcome
import dev.az1nn.chi.recognition.RecognitionState

/**
 * Native IME with a keyboard-owned in-memory stylus canvas.
 * Ink is not recognized, persisted, transmitted or committed to an editor.
 */
class ChiInputMethodService : InputMethodService() {
    private val inkSession = InkSession()
    // Resolve the active InputConnection only at action time.
    private val editorGateway = EditorGateway(
        commitText = { text -> currentInputConnection?.commitText(text, 1) == true },
        deleteOneBeforeCursor = { currentInputConnection?.deleteSurroundingText(1, 0) == true }
    )
    private var statusLabel: TextView? = null
    private var inkCanvas: InkCanvasView? = null
    private var clearButton: Button? = null
    private var previewLabel: TextView? = null
    private var candidateChoices: LinearLayout? = null
    private var insertButton: Button? = null
    private var selectedCandidateIndex = 0

    // T013/T014 has not selected a model or SDK. Fail closed; no fake candidates.
    private val recognitionCoordinator = RecognitionCoordinator(object : InkRecognizer {
        override fun recognize(
            strokes: List<InkStroke>,
            onCompleted: (RecognitionOutcome) -> Unit
        ): RecognitionCancellation {
            onCompleted(RecognitionOutcome.ModelUnavailable)
            return RecognitionCancellation { }
        }
    })

    override fun onCreateInputView(): View {
        fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

        // Never replay ink that was captured in an earlier editor/view.
        inkSession.clear()
        recognitionCoordinator.invalidate()
        selectedCandidateIndex = 0
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), dp(8), dp(12), dp(8))
            setBackgroundColor(Color.rgb(19, 22, 29))
        }

        val header = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        header.addView(TextView(this).apply {
            text = getString(R.string.ime_title)
            textSize = 16f
            setTextColor(Color.WHITE)
        }, LinearLayout.LayoutParams(0, dp(52), 1f))
        header.addView(Button(this).apply {
            text = getString(R.string.voice_icon)
            contentDescription = getString(R.string.voice_unavailable)
            isEnabled = false  // Visible by design, never implies a working microphone.
        }, LinearLayout.LayoutParams(dp(60), dp(52)))
        root.addView(header)

        statusLabel = TextView(this).apply {
            textSize = 13f
            setTextColor(Color.LTGRAY)
        }.also { root.addView(it) }

        val canvas = InkCanvasView(this, inkSession, onInkChanged = { invalidateCandidates() }).apply {
            contentDescription = getString(R.string.ink_canvas_accessibility)
        }
        inkCanvas = canvas
        root.addView(canvas, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, dp(164)
        ))

        previewLabel = TextView(this).apply {
            setText(R.string.candidate_unavailable)
            textSize = 14f
            setTextColor(Color.WHITE)
            setPadding(0, dp(8), 0, dp(8))
            accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE
        }.also { root.addView(it) }

        candidateChoices = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
        }.also { root.addView(it) }

        val buttons = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        fun addKey(label: Int, description: Int = label, action: () -> Unit): Button {
            val button = Button(this).apply {
                text = getString(label)
                contentDescription = getString(description)
                setOnClickListener { action() }
            }
            buttons.addView(button, LinearLayout.LayoutParams(0, dp(52), 1f))
            return button
        }
        clearButton = addKey(R.string.ink_clear) { clearInk() }
        addKey(R.string.key_space) { editorGateway.insertSpace() }
        addKey(R.string.key_delete) { editorGateway.deleteBeforeCursor() }
        addKey(R.string.key_switch, R.string.key_switch_accessibility) {
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
        }
        root.addView(buttons)

        insertButton = Button(this).apply {
            setText(R.string.candidate_insert)
            contentDescription = getString(R.string.candidate_insert_accessibility)
            isEnabled = false
            setOnClickListener {
                val committed = recognitionCoordinator.acceptCandidate(
                    selectedCandidateIndex,
                    editorGateway.candidateEpoch()
                ) { value, epoch -> editorGateway.commitCandidate(value, epoch) }
                if (committed) clearInk() else invalidateCandidates()
            }
        }.also {
            root.addView(it, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(52)
            ))
        }

        updateStatus(currentInputEditorInfo)
        renderCandidateState()
        return root
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        editorGateway.onEditorStarted(attribute?.inputType)
        clearInk()
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        editorGateway.onEditorStarted(info?.inputType)
        clearInk()
        updateStatus(info)
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        editorGateway.onEditorFinished()
        clearInk()
        super.onFinishInputView(finishingInput)
    }

    override fun onFinishInput() {
        editorGateway.onEditorFinished()
        clearInk()
        super.onFinishInput()
    }

    override fun onDestroy() {
        editorGateway.onEditorFinished()
        clearInk()
        inkCanvas = null
        statusLabel = null
        clearButton = null
        previewLabel = null
        candidateChoices = null
        insertButton = null
        super.onDestroy()
    }

    private fun clearInk() {
        recognitionCoordinator.invalidate()
        inkCanvas?.reset() ?: inkSession.clear()
        selectedCandidateIndex = 0
        renderCandidateState()
    }

    /** New strokes discard pending recognition; no previous editor data is reused. */
    private fun invalidateCandidates() {
        recognitionCoordinator.invalidate()
        selectedCandidateIndex = 0
        renderCandidateState()
    }

    /** T011 preview; T014 must marshal recognizer callbacks back to the UI thread. */
    private fun renderCandidateState() {
        val epoch = editorGateway.candidateEpoch()
        val state = recognitionCoordinator.state
        val ready = (state as? RecognitionState.Ready)?.takeIf {
            it.editorEpoch == epoch && it.choices.isNotEmpty()
        }
        val choices = ready?.choices.orEmpty()
        if (selectedCandidateIndex !in choices.indices) selectedCandidateIndex = 0

        candidateChoices?.removeAllViews()
        if (ready != null) {
            for ((index, choice) in choices.withIndex()) {
                candidateChoices?.addView(Button(this).apply {
                    text = choice
                    isAllCaps = false
                    contentDescription = getString(
                        R.string.candidate_choice_accessibility, index + 1, choice
                    )
                    setOnClickListener {
                        selectedCandidateIndex = index
                        renderCandidateState()
                    }
                }, LinearLayout.LayoutParams(
                    0, (48 * resources.displayMetrics.density).toInt(), 1f
                ))
            }
        }

        previewLabel?.text = when {
            epoch == null -> getString(R.string.candidate_sensitive)
            ready != null -> getString(
                R.string.candidate_preview, choices[selectedCandidateIndex]
            )
            state == RecognitionState.Processing -> getString(R.string.candidate_processing)
            state == RecognitionState.Failed -> getString(R.string.candidate_failed)
            else -> getString(R.string.candidate_unavailable)
        }
        insertButton?.isEnabled = ready != null
    }

    private fun updateStatus(info: EditorInfo?) {
        val sensitive = info == null || EditorPrivacyPolicy.isSensitive(info.inputType)
        if (sensitive) clearInk()
        inkCanvas?.isEnabled = !sensitive
        clearButton?.isEnabled = !sensitive
        statusLabel?.setText(if (sensitive) R.string.ime_sensitive else R.string.ime_ink_status)
    }
}
