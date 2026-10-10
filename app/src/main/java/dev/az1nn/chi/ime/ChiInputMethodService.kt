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

/**
 * First installable IME boundary. No ink capture or recognition SDK is present.
 * Only explicit editor actions (space/delete) are exposed while T009–T014 remain open.
 */
class ChiInputMethodService : InputMethodService() {
    private var statusLabel: TextView? = null

    override fun onCreateInputView(): View {
        fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(12), dp(8), dp(12), dp(8))
            setBackgroundColor(Color.rgb(248, 248, 248))
        }
        root.addView(TextView(this).apply {
            text = getString(R.string.ime_title)
            textSize = 16f
            setTextColor(Color.BLACK)
        })
        val status = TextView(this).apply {
            text = getString(R.string.ime_placeholder)
            textSize = 13f
            setTextColor(Color.DKGRAY)
        }
        statusLabel = status
        root.addView(status)

        val buttons = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        fun addKey(label: Int, description: Int = label, action: () -> Unit) {
            val button = Button(this).apply {
                text = getString(label)
                contentDescription = getString(description)
                setOnClickListener { action() }
            }
            buttons.addView(button, LinearLayout.LayoutParams(0, dp(52), 1f))
        }
        addKey(R.string.key_space) {
            currentInputConnection?.commitText(" ", 1)
        }
        addKey(R.string.key_delete) {
            currentInputConnection?.deleteSurroundingText(1, 0)
        }
        addKey(R.string.key_switch, R.string.key_switch_accessibility) {
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
        }
        root.addView(buttons)
        updateStatus(currentInputEditorInfo)
        return root
    }

    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        updateStatus(info)
    }

    private fun updateStatus(info: EditorInfo?) {
        val sensitive = info == null || EditorPrivacyPolicy.isSensitive(info.inputType)
        statusLabel?.setText(if (sensitive) R.string.ime_sensitive else R.string.ime_placeholder)
    }
}
