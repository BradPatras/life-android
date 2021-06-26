package io.github.bradpatras.life.ui.main.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import io.github.bradpatras.life.R

class DotBoardView : FrameLayout {

    class Dot(val x: Int, val y: Int)

    var dots: Array<Dot> = emptyArray()

    private val dotSpacing: Int = 10
    private val dotSize: Int = 20

    private var mPosX = 0f
    private var mPosY = 0f

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
        // initial setup
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