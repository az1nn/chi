package dev.az1nn.chi.recognition

import dev.az1nn.chi.ink.InkPoint
import dev.az1nn.chi.ink.InkStroke
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RecognitionCoordinatorTest {
    private class FakeRecognizer : InkRecognizer {
        data class Request(
            val strokes: List<InkStroke>,
            val complete: (RecognitionOutcome) -> Unit,
            var cancelled: Boolean = false
        )
        val requests = mutableListOf<Request>()

        override fun recognize(
            strokes: List<InkStroke>,
            onCompleted: (RecognitionOutcome) -> Unit
        ): RecognitionCancellation {
            val request = Request(strokes, onCompleted)
            requests.add(request)
            return RecognitionCancellation { request.cancelled = true }
        }
    }

    private fun strokes() = listOf(
        InkStroke(listOf(InkPoint(1f, 2f, 3L), InkPoint(2f, 3f, 4L)))
    )

    @Test fun noEditorOrEmptyInkNeverCallsRecognizer() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        assertFalse(coordinator.submit(strokes(), null))
        assertFalse(coordinator.submit(emptyList(), 1L))
        assertFalse(coordinator.submit(listOf(InkStroke(emptyList())), 1L))
        assertTrue(fake.requests.isEmpty())
        assertEquals(RecognitionState.Idle, coordinator.state)
    }

    @Test fun completionNeverCommitsWithoutExplicitAcceptance() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        assertTrue(coordinator.submit(strokes(), 5L))
        assertEquals(RecognitionState.Processing, coordinator.state)
        fake.requests.single().complete(RecognitionOutcome.Candidates(listOf("bom dia")))
        assertEquals(RecognitionState.Ready(listOf("bom dia"), 5L), coordinator.state)
    }

    @Test fun canceledRequestCannotResurrectCandidates() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        coordinator.submit(strokes(), 1L)
        coordinator.invalidate()
        assertTrue(fake.requests.single().cancelled)
        fake.requests.single().complete(RecognitionOutcome.Candidates(listOf("stale")))
        assertEquals(RecognitionState.Idle, coordinator.state)
    }

    @Test fun onlyMostRecentRequestMayReturnCandidates() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        coordinator.submit(strokes(), 3L)
        coordinator.submit(strokes(), 3L)
        assertTrue(fake.requests.first().cancelled)
        fake.requests.first().complete(RecognitionOutcome.Candidates(listOf("wrong")))
        assertEquals(RecognitionState.Processing, coordinator.state)
        fake.requests.last().complete(RecognitionOutcome.Candidates(listOf("right")))
        assertEquals(RecognitionState.Ready(listOf("right"), 3L), coordinator.state)
    }

    @Test fun focusEpochMismatchConsumesCandidateWithoutCommit() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        coordinator.submit(strokes(), 7L)
        fake.requests.single().complete(RecognitionOutcome.Candidates(listOf("private")))
        var writes = 0
        assertFalse(coordinator.acceptCandidate(0, 8L) { _, _ -> writes++; true })
        assertEquals(0, writes)
        assertEquals(RecognitionState.Idle, coordinator.state)
    }

    @Test fun explicitCommitIsSingleUseEvenAfterRapidSecondTap() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        coordinator.submit(strokes(), 4L)
        fake.requests.single().complete(RecognitionOutcome.Candidates(listOf("oi")))
        val writes = mutableListOf<String>()
        assertTrue(coordinator.acceptCandidate(0, 4L) { value, epoch ->
            assertEquals(4L, epoch)
            writes.add(value)
            true
        })
        assertFalse(coordinator.acceptCandidate(0, 4L) { value, _ ->
            writes.add(value)
            true
        })
        assertEquals(listOf("oi"), writes)
    }

    @Test fun invalidIndexDoesNotCommitAndValidChoiceStillWorks() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        coordinator.submit(strokes(), 1L)
        fake.requests.single().complete(RecognitionOutcome.Candidates(listOf("um", "dois")))
        assertFalse(coordinator.acceptCandidate(2, 1L) { _, _ -> true })
        assertTrue(coordinator.acceptCandidate(1, 1L) { value, _ -> value == "dois" })
    }

    @Test fun blankCandidatesFailClosedAndValidCandidatesAreBounded() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        coordinator.submit(strokes(), 1L)
        fake.requests.last().complete(RecognitionOutcome.Candidates(listOf("", " ", "\n")))
        assertEquals(RecognitionState.Failed, coordinator.state)
        coordinator.submit(strokes(), 2L)
        fake.requests.last().complete(
            RecognitionOutcome.Candidates(listOf(" a ", "a", "b", "c", "d", "e", "f"))
        )
        assertEquals(RecognitionState.Ready(listOf("a", "b", "c", "d", "e"), 2L), coordinator.state)
    }

    @Test fun unavailableAndFailureAreExplicitSafeStates() {
        val fake = FakeRecognizer()
        val coordinator = RecognitionCoordinator(fake)
        coordinator.submit(strokes(), 1L)
        fake.requests.last().complete(RecognitionOutcome.ModelUnavailable)
        assertEquals(RecognitionState.ModelUnavailable, coordinator.state)
        coordinator.submit(strokes(), 1L)
        fake.requests.last().complete(RecognitionOutcome.Failed)
        assertEquals(RecognitionState.Failed, coordinator.state)
    }

    @Test fun synchronousRecognizerCallbackIsHandled() {
        val synchronous = object : InkRecognizer {
            override fun recognize(
                strokes: List<InkStroke>,
                onCompleted: (RecognitionOutcome) -> Unit
            ): RecognitionCancellation {
                onCompleted(RecognitionOutcome.Candidates(listOf("pronto")))
                return RecognitionCancellation { }
            }
        }
        val coordinator = RecognitionCoordinator(synchronous)
        assertTrue(coordinator.submit(strokes(), 2L))
        assertEquals(RecognitionState.Ready(listOf("pronto"), 2L), coordinator.state)
    }

    @Test fun adapterExceptionDoesNotEscapeIntoIme() {
        val throwing = object : InkRecognizer {
            override fun recognize(
                strokes: List<InkStroke>,
                onCompleted: (RecognitionOutcome) -> Unit
            ): RecognitionCancellation = throw IllegalStateException("private data")
        }
        val coordinator = RecognitionCoordinator(throwing)
        assertTrue(coordinator.submit(strokes(), 2L))
        assertEquals(RecognitionState.Failed, coordinator.state)
    }
}
