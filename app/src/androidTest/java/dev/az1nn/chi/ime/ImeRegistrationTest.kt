package dev.az1nn.chi.ime

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

/** Android service registration and selection; does not prove handwriting recognition. */
@RunWith(AndroidJUnit4::class)
class ImeRegistrationTest {
    private val context get() = InstrumentationRegistry.getInstrumentation().targetContext

    @Test fun registersAProtectedImeService() {
        val matches = context.packageManager.queryIntentServices(
            Intent("android.view.InputMethod").setPackage(context.packageName),
            PackageManager.MATCH_ALL
        )
        assertTrue(matches.any {
            it.serviceInfo.name == ChiInputMethodService::class.java.name &&
                it.serviceInfo.permission == Manifest.permission.BIND_INPUT_METHOD
        })
    }

    @Test fun enabledImeCanBeSelected() {
        val manager = context.getSystemService(InputMethodManager::class.java)
        val chi = manager.enabledInputMethodList.firstOrNull {
            it.packageName == context.packageName &&
                it.serviceName == ChiInputMethodService::class.java.name
        }
        assertTrue("Emulator must first enable Chi with adb", chi != null)
        assertEquals(
            chi!!.id,
            Settings.Secure.getString(context.contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
        )
    }
}
