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

    override fun onCreateInputView(): View {
        fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

        // Never replay ink that was captured in an earlier editor/view.
        inkSession.clear()
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

        val canvas = InkCanvasView(this, inkSession).apply {
            contentDescription = getString(R.string.ink_canvas_accessibility)
        }
        inkCanvas = canvas
        root.addView(canvas, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, dp(164)
        ))

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
        clearButton = addKey(R.string.ink_clear) { inkSession.clear(); canvas.invalidate() }
        addKey(R.string.key_space) { editorGateway.insertSpace() }
        addKey(R.string.key_delete) { editorGateway.deleteBeforeCursor() }
        addKey(R.string.key_switch, R.string.key_switch_accessibility) {
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
        }
        root.addView(buttons)

        updateStatus(currentInputEditorInfo)
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
        super.onDestroy()
    }

    private fun clearInk() {
        inkCanvas?.reset() ?: inkSession.clear()
    }

    private fun updateStatus(info: EditorInfo?) {
        val sensitive = info == null || EditorPrivacyPolicy.isSensitive(info.inputType)
        if (sensitive) clearInk()
        inkCanvas?.isEnabled = !sensitive
        clearButton?.isEnabled = !sensitive
        statusLabel?.setText(if (sensitive) R.string.ime_sensitive else R.string.ime_ink_status)
    }
}
