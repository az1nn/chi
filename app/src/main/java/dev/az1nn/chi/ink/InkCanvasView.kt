package dev.az1nn.chi.ink

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent
import android.view.View

/**
 * Keyboard-owned canvas: stylus strokes are kept only in the in-memory InkSession.
 * No recognition, storage, networking, or text insertion happens here.
 * Finger/palm input is deliberately ignored pending a separate acceptance decision.
 */
class InkCanvasView(
    context: Context,
    private val session: InkSession,
    private val onInkChanged: () -> Unit = {}
) : View(context) {
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.rgb(152, 226, 220)
        style = Paint.Style.STROKE
        strokeWidth = 4f * resources.displayMetrics.density
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }
    private val dotPaint = Paint(strokePaint).apply { style = Paint.Style.FILL }
    private var pointerId: Int? = null

    init {
        setBackgroundColor(Color.rgb(29, 34, 44))
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_YES
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (stroke in session.visibleStrokes()) {
            val points = stroke.points
            if (points.size == 1) {
                canvas.drawCircle(points[0].x, points[0].y, strokePaint.strokeWidth / 2f, dotPaint)
            } else if (points.size > 1) {
                val path = Path().apply {
                    moveTo(points[0].x, points[0].y)
                    for (i in 1 until points.size) lineTo(points[i].x, points[i].y)
                }
                canvas.drawPath(path, strokePaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isEnabled) return false

        if (event.actionMasked == MotionEvent.ACTION_CANCEL) {
            if (pointerId == null) return false
            session.cancel()
            pointerId = null
            onInkChanged()
            invalidate()
            return true
        }

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                if (event.getToolType(0) != MotionEvent.TOOL_TYPE_STYLUS) return false
                if (!session.begin(event.getX(0), event.getY(0), event.eventTime)) return false
                pointerId = event.getPointerId(0)
                onInkChanged()
                invalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val active = pointerId ?: return false
                val i = event.findPointerIndex(active)
                if (i < 0) {
                    session.cancel()
                    pointerId = null
                    onInkChanged()
                    invalidate()
                    return true
                }
                // The view owns only the accepted stylus pointer; palm contacts are ignored.
                if (session.append(event.getX(i), event.getY(i), event.eventTime)) onInkChanged()
                invalidate()
                return true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                val active = pointerId ?: return false
                val i = event.actionIndex
                if (event.getPointerId(i) != active) return true
                session.end(event.getX(i), event.getY(i), event.eventTime)
                pointerId = null
                onInkChanged()
                invalidate()
                performClick()
                return true
            }
            MotionEvent.ACTION_POINTER_DOWN -> return pointerId != null
        }
        return pointerId != null
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    /** Always abandon in-progress ink when a field switches or the input view closes. */
    fun reset() {
        pointerId = null
        session.clear()
        invalidate()
    }
}
