package io.github.bradpatras.life.ui.main.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.view.children


class ScrollingLayout : FrameLayout {
    private var touchPosX = 0f
    private var touchPosY = 0f

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
        // initial setup
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchPosX = ev.rawX
                touchPosY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                children.iterator().forEach {
                    it.x += ev.rawX - touchPosX
                    it.y += ev.rawY - touchPosY
                    touchPosX = ev.rawX
                    touchPosY = ev.rawY
                    it.invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_CANCEL -> {
            }
            MotionEvent.ACTION_POINTER_UP -> {
            }
        }
        return true
    }
}