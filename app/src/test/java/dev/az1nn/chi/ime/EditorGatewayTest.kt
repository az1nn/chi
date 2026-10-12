package dev.az1nn.chi.ime

import android.text.InputType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EditorGatewayTest {
    @Test fun absentEditorRefusesAllActions() {
        var calls = 0
        val gateway = EditorGateway({ calls++; true }, { calls++; true })
        assertNull(gateway.candidateEpoch())
        assertFalse(gateway.insertSpace())
        assertFalse(gateway.deleteBeforeCursor())
        assertFalse(gateway.commitCandidate("unexpected", 0L))
        assertEquals(0, calls)
    }

    @Test fun activeEditorDelegatesExplicitSpaceAndDelete() {
        val committed = mutableListOf<String>()
        var deleted = 0
        val gateway = EditorGateway({ committed.add(it); true }, { deleted++; true })
        gateway.onEditorStarted(InputType.TYPE_CLASS_TEXT)
        assertTrue(gateway.insertSpace())
        assertTrue(gateway.deleteBeforeCursor())
        assertEquals(listOf(" "), committed)
        assertEquals(1, deleted)
    }

    @Test fun sensitiveEditorRefusesInkCandidateButAllowsManualEditing() {
        val committed = mutableListOf<String>()
        val gateway = EditorGateway({ committed.add(it); true }, { true })
        gateway.onEditorStarted(InputType.TYPE_CLASS_TEXT)
        val oldEpoch = gateway.candidateEpoch()!!
        gateway.onEditorStarted(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        assertNull(gateway.candidateEpoch())
        assertFalse(gateway.commitCandidate("private", oldEpoch))
        assertTrue(gateway.insertSpace())
        assertEquals(listOf(" "), committed)
    }

    @Test fun editorChangeAndFinishInvalidateCandidateEpoch() {
        val committed = mutableListOf<String>()
        val gateway = EditorGateway({ committed.add(it); true }, { true })
        gateway.onEditorStarted(InputType.TYPE_CLASS_TEXT)
        val first = gateway.candidateEpoch()!!
        gateway.onEditorStarted(InputType.TYPE_CLASS_TEXT)
        assertFalse(gateway.commitCandidate("stale", first))
        val second = gateway.candidateEpoch()!!
        assertTrue(gateway.commitCandidate("valid", second))
        gateway.onEditorFinished()
        assertFalse(gateway.commitCandidate("after close", second))
        assertFalse(gateway.insertSpace())
        assertEquals(listOf("valid"), committed)
    }

    @Test fun unsupportedInputAndBlankCandidateFailClosed() {
        var writes = 0
        val gateway = EditorGateway({ writes++; true }, { true })
        gateway.onEditorStarted(0)
        assertNull(gateway.candidateEpoch())
        assertFalse(gateway.insertSpace())
        gateway.onEditorStarted(InputType.TYPE_CLASS_TEXT)
        val epoch = gateway.candidateEpoch()!!
        assertFalse(gateway.commitCandidate("   ", epoch))
        assertEquals(0, writes)
    }

    @Test fun missingCurrentConnectionDoesNotClaimSuccess() {
        val gateway = EditorGateway({ false }, { false })
        gateway.onEditorStarted(InputType.TYPE_CLASS_TEXT)
        assertFalse(gateway.insertSpace())
        assertFalse(gateway.deleteBeforeCursor())
        assertFalse(gateway.commitCandidate("test", gateway.candidateEpoch()!!))
    }
}
