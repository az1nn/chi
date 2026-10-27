package dev.az1nn.chi.ink

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class InkSessionTest {
    @Test fun orderedMultiStrokeInk() {
        val ink = InkSession()
        assertTrue(ink.begin(1f, 2f, 10))
        assertTrue(ink.append(2f, 3f, 20))
        assertTrue(ink.end(3f, 4f, 30))
        assertTrue(ink.begin(4f, 5f, 40))
        assertTrue(ink.end(5f, 6f, 50))
        assertEquals(2, ink.strokeCount)
        assertEquals(listOf(10L, 20L, 30L), ink.snapshot()[0].points.map { it.timeMillis })
        assertEquals(listOf(40L, 50L), ink.snapshot()[1].points.map { it.timeMillis })
    }

    @Test fun cancelDropsOnlyActiveStroke() {
        val ink = InkSession()
        ink.begin(0f, 0f, 1)
        ink.end(2f, 2f, 2)
        ink.begin(3f, 3f, 3)
        ink.cancel()
        assertEquals(1, ink.strokeCount)
        assertEquals(1, ink.visibleStrokes().size)
    }

    @Test fun clearDropsFinishedAndInProgressInk() {
        val ink = InkSession()
        ink.begin(0f, 0f, 1)
        ink.end(1f, 1f, 2)
        ink.begin(3f, 3f, 4)
        ink.clear()
        assertEquals(0, ink.strokeCount)
        assertFalse(ink.hasInk)
        assertTrue(ink.snapshot().isEmpty())
        assertTrue(ink.visibleStrokes().isEmpty())
    }

    @Test fun snapshotsCannotBeMutatedByLaterEdits() {
        val ink = InkSession()
        ink.begin(0f, 0f, 1)
        ink.end(1f, 1f, 2)
        val snapshot = ink.snapshot()
        ink.clear()
        assertEquals(2, snapshot.single().points.size)
        assertTrue(ink.snapshot().isEmpty())
    }

    @Test fun rejectNonFiniteCoordinatesAndOutOfOrderEvents() {
        val ink = InkSession()
        assertFalse(ink.begin(Float.NaN, 1f, 1))
        assertFalse(ink.begin(1f, Float.POSITIVE_INFINITY, 1))
        assertFalse(ink.begin(1f, 1f, -1))
        assertTrue(ink.begin(0f, 0f, 9))
        assertFalse(ink.append(1f, 1f, 8))
        assertFalse(ink.end(1f, 1f, 7))
        assertTrue(ink.snapshot().isEmpty())
        assertFalse(ink.hasInk)
    }

    @Test fun boundStrokeCountAndPointCount() {
        val ink = InkSession(maxStrokes = 1, maxPointsPerStroke = 2)
        assertTrue(ink.begin(1f, 1f, 1))
        assertTrue(ink.append(2f, 2f, 2))
        assertFalse(ink.append(3f, 3f, 3))
        assertTrue(ink.end(4f, 4f, 4))
        assertEquals(2, ink.snapshot().single().points.size)
        assertFalse(ink.begin(5f, 5f, 5))
        assertTrue(ink.undoLast())
        assertTrue(ink.begin(5f, 5f, 5))
    }

    @Test fun undoCurrentThenCommittedInk() {
        val ink = InkSession()
        ink.begin(0f, 0f, 1)
        ink.end(0f, 0f, 2)
        ink.begin(3f, 4f, 4)
        assertTrue(ink.undoLast())
        assertEquals(1, ink.strokeCount)
        assertTrue(ink.undoLast())
        assertFalse(ink.undoLast())
    }
}
