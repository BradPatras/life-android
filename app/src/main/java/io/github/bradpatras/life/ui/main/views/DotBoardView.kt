package io.github.bradpatras.life.ui.main.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import io.github.bradpatras.life.R

class DotBoardView : View {

    class Dot(val x: Int, val y: Int)

    var dots: Array<Dot> = emptyArray()

    private val dotSpacing: Int = 10
    private val dotSize: Int = 20

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
       setup()
    }

    private fun setup() {

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        dots.forEach { dot ->

            context.getDrawable(R.drawable.dot)?.let { drawable ->
                drawable.bounds = getDotRect(dot)
                drawable.draw(canvas)
            }
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