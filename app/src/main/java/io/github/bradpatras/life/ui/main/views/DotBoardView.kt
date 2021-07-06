package io.github.bradpatras.life.ui.main.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import io.github.bradpatras.life.R

class DotBoardView : FrameLayout {

    class Dot(val x: Int, val y: Int)

    var dots: Array<Dot> = emptyArray()
    val gridPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 1f
    }

    val dotPaint = Paint().apply {
        color = Color.BLACK
    }

    private val dotSpacing: Int = 0
    private val dotSize: Int = 16

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
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // initial setup
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        dots.forEach { dot ->

//            context.getDrawable(R.drawable.dot)?.let { drawable ->
//                drawable.bounds = getDotRect(dot)
//                drawable.draw(canvas)
//            }
            canvas.drawRect(getDotRect(dot), dotPaint)
        }

        val hLines = width / (dotSize + dotSpacing)
        for (index in 0..hLines) {
            canvas.drawLine(
                0f,
                (index * (dotSize + dotSpacing)).toFloat() - 1f,
                width.toFloat(),
                (index * (dotSize + dotSpacing)).toFloat() - 1f,
                gridPaint
            )
        }

        val vLines = height / (dotSize + dotSpacing)
        for (index in 0..vLines) {
            canvas.drawLine(
                (index * (dotSize + (dotSpacing))).toFloat() - 1f,
                0f,
                (index * (dotSize + (dotSpacing))).toFloat() - 1f,
                height.toFloat() ,
                gridPaint
            )
        }
    }

    private fun getDotRect(dot: Dot): Rect {
        return Rect(
            dot.x * (dotSize + dotSpacing),
            dot.y * (dotSize + dotSpacing),
            dot.x * (dotSize + dotSpacing) + dotSize,
            dot.y * (dotSize + dotSpacing) + dotSize
        )
    }
}