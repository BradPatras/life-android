package io.github.bradpatras.life.ui.main.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.bradpatras.life.R

class DotBoardView : FrameLayout {

    var tappedDotLiveData: MutableLiveData<Dot?> = MutableLiveData(null)

    var dots: Array<Dot> = emptyArray()

    private val gridPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = gridLineWidth
        isAntiAlias = false
    }

    private val dotPaint = Paint().apply {
        color = Color.BLACK
    }

    private val dotSpacing: Int = 0
    private val dotSize: Int = 16
    private val gridLineWidth: Float = 4f

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
            context,
            attrs,
            defStyle
    ) {
        init()
    }

    private fun init() {
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        dots.forEach { dot ->
            canvas.drawRect(getDotRect(dot), dotPaint)
        }

        val hLines = width / (dotSize + dotSpacing)
        val vLines = height / (dotSize + dotSpacing)

        for (index in 0..hLines) {
            canvas.drawLine(
                    0f,
                    (index * (dotSize + dotSpacing)).toFloat(),
                    (vLines * dotSize).toFloat(),
                    (index * (dotSize + dotSpacing)).toFloat(),
                    gridPaint
            )
        }

        for (index in 0..vLines) {
            canvas.drawLine(
                    (index * (dotSize + (dotSpacing))).toFloat(),
                    0f,
                    (index * (dotSize + (dotSpacing))).toFloat(),
                    (hLines * dotSize).toFloat(),
                    gridPaint
            )
        }
    }

    private fun getDotRect(dot: Dot): Rect {
        val left = dot.x * (dotSize + dotSpacing)
        val top = dot.y * (dotSize + dotSpacing)
        val right = left + dotSize
        val bottom = top + dotSize

        return Rect(left, top, right, bottom)
    }

    private fun createDotAtPoint(point: Point): Dot {
        val dotX = point.x / (dotSize + dotSpacing)
        val dotY = point.y / (dotSize + dotSpacing)

        return Dot(dotX, dotY)
    }
}