package dev.az1nn.chi.ime

/**
 * Editor actions, without retaining an InputConnection or logging content.
 * Candidate commits require the current eligible editor's generation token.
 */
internal class EditorGateway(
    private val commitText: (String) -> Boolean,
    private val deleteOneBeforeCursor: () -> Boolean
) {
    private var generation = 0L
    private var activeEditor = false
    private var candidateAllowed = false

    fun onEditorStarted(inputType: Int?) {
        generation++
        activeEditor = inputType != null && inputType != 0
        candidateAllowed = activeEditor && inputType != null &&
            !EditorPrivacyPolicy.isSensitive(inputType)
    }

    fun onEditorFinished() {
        generation++
        activeEditor = false
        candidateAllowed = false
    }

    fun candidateEpoch(): Long? = generation.takeIf { candidateAllowed }

    fun commitCandidate(text: String, expectedEpoch: Long): Boolean {
        if (!candidateAllowed || expectedEpoch != generation || text.isBlank()) return false
        return commitText(text)
    }

    // Explicit editing keys remain available in a sensitive editor, not ink/candidates.
    fun insertSpace(): Boolean = activeEditor && commitText(" ")
    fun deleteBeforeCursor(): Boolean = activeEditor && deleteOneBeforeCursor()
}
