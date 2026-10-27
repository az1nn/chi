package dev.az1nn.chi.ink

/**
 * Pure Kotlin ink data model. The model never serializes or sends writing content.
 * Coordinates are in the drawing View's local pixel space.
 */
data class InkPoint(val x: Float, val y: Float, val timeMillis: Long)
data class InkStroke(val points: List<InkPoint>)

/**
 * Mutable single-pointer session with immutable snapshots for later recognizer adapters.
 * Completed strokes are bounded; interrupted strokes are discarded, never committed.
 */
class InkSession(
    private val maxStrokes: Int = 64,
    private val maxPointsPerStroke: Int = 2048
) {
    init {
        require(maxStrokes > 0 && maxPointsPerStroke > 0)
    }

    private val finished = mutableListOf<InkStroke>()
    private var active: MutableList<InkPoint>? = null

    val strokeCount: Int get() = finished.size
    val hasInk: Boolean get() = finished.isNotEmpty() || active != null

    fun begin(x: Float, y: Float, timeMillis: Long): Boolean {
        if (active != null || finished.size >= maxStrokes || !isValid(x, y, timeMillis)) return false
        active = mutableListOf(InkPoint(x, y, timeMillis))
        return true
    }

    fun append(x: Float, y: Float, timeMillis: Long): Boolean {
        val points = active ?: return false
        if (!isValid(x, y, timeMillis) ||
            timeMillis < points.last().timeMillis ||
            points.size >= maxPointsPerStroke
        ) return false
        points.add(InkPoint(x, y, timeMillis))
        return true
    }

    fun end(x: Float, y: Float, timeMillis: Long): Boolean {
        val points = active ?: return false
        if (!isValid(x, y, timeMillis) || timeMillis < points.last().timeMillis) {
            cancel()
            return false
        }
        if (points.size < maxPointsPerStroke) {
            points.add(InkPoint(x, y, timeMillis))
        }
        finished.add(InkStroke(points.toList()))
        active = null
        return true
    }

    /** Used for ACTION_CANCEL, focus changes and IME lifecycle interruptions. */
    fun cancel() {
        active = null
    }

    /** Snapshot must not change when later strokes arrive or the session clears. */
    fun snapshot(): List<InkStroke> = finished.map { InkStroke(it.points.toList()) }

    /** For rendering only: also includes the active (uncommitted) stroke. */
    fun visibleStrokes(): List<InkStroke> {
        val rendering = snapshot().toMutableList()
        active?.let { rendering.add(InkStroke(it.toList())) }
        return rendering
    }

    fun undoLast(): Boolean {
        if (active != null) {
            cancel()
            return true
        }
        if (finished.isEmpty()) return false
        finished.removeAt(finished.lastIndex)
        return true
    }

    fun clear() {
        active = null
        finished.clear()
    }

    private fun isValid(x: Float, y: Float, t: Long) =
        x.isFinite() && y.isFinite() && t >= 0L
}
