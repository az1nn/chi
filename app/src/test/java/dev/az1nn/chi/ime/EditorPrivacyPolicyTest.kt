package dev.az1nn.chi.ime

import android.text.InputType
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EditorPrivacyPolicyTest {
    @Test fun normalTextIsNotSensitive() {
        assertFalse(EditorPrivacyPolicy.isSensitive(InputType.TYPE_CLASS_TEXT))
    }

    @Test fun textPasswordsAreSensitive() {
        for (variation in listOf(
            InputType.TYPE_TEXT_VARIATION_PASSWORD,
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD,
            InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
        )) {
            assertTrue(EditorPrivacyPolicy.isSensitive(InputType.TYPE_CLASS_TEXT or variation))
        }
    }

    @Test fun numericPasswordIsSensitive() {
        assertTrue(EditorPrivacyPolicy.isSensitive(
            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
        ))
    }

    @Test fun normalNumberIsNotSensitive() {
        assertFalse(EditorPrivacyPolicy.isSensitive(InputType.TYPE_CLASS_NUMBER))
    }
}
