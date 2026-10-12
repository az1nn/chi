package dev.az1nn.chi

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import dev.az1nn.chi.ime.ChiInputMethodService

/** User-driven Android IME setup; never enables or selects a keyboard silently. */
class SetupActivity : Activity() {
    private lateinit var statusText: TextView
    private lateinit var selectButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fun dp(n: Int) = (n * resources.displayMetrics.density).toInt()
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(24), dp(20), dp(24))
            setBackgroundColor(Color.rgb(19, 22, 29))
        }
        fun label(res: Int, size: Float) = TextView(this).apply {
            setText(res)
            textSize = size
            setTextColor(Color.WHITE)
            setPadding(0, 0, 0, dp(14))
        }
        root.addView(label(R.string.setup_title, 24f))
        root.addView(label(R.string.setup_description, 15f))
        root.addView(label(R.string.setup_privacy_warning, 14f))
        statusText = label(R.string.setup_status_disabled, 15f)
        statusText.accessibilityLiveRegion = View.ACCESSIBILITY_LIVE_REGION_POLITE
        root.addView(statusText)
        root.addView(Button(this).apply {
            setText(R.string.setup_enable)
            setOnClickListener {
                try {
                    startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS))
                } catch (_: ActivityNotFoundException) {
                    statusText.setText(R.string.setup_settings_unavailable)
                } catch (_: SecurityException) {
                    statusText.setText(R.string.setup_settings_unavailable)
                }
            }
        })
        selectButton = Button(this).apply {
            setText(R.string.setup_select)
            setOnClickListener {
                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showInputMethodPicker()
            }
        }
        root.addView(selectButton)
        root.addView(label(R.string.setup_test_label, 15f))
        root.addView(EditText(this).apply {
            hint = getString(R.string.setup_test_hint)
            setSingleLine(true)
            setTextColor(Color.WHITE)
            setHintTextColor(Color.LTGRAY)
            contentDescription = getString(R.string.setup_test_hint)
        })
        setContentView(ScrollView(this).apply { addView(root) })
        refreshState()
    }

    override fun onResume() {
        super.onResume()
        if (::statusText.isInitialized) refreshState()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && ::statusText.isInitialized) refreshState()
    }

    private fun refreshState() {
        val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val chi = manager.enabledInputMethodList.firstOrNull {
            it.packageName == packageName && it.serviceName == ChiInputMethodService::class.java.name
        }
        val selectedId = Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        selectButton.isEnabled = chi != null
        statusText.setText(when {
            chi == null -> R.string.setup_status_disabled
            chi.id == selectedId -> R.string.setup_status_selected
            else -> R.string.setup_status_enabled
        })
    }
}
