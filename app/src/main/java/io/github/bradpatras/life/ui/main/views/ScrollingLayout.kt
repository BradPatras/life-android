package io.github.bradpatras.life.ui.main.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import androidx.core.view.children


class ScrollingLayout : FrameLayout {
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
        // initial setup
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mPosX = ev.rawX
                mPosY = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                children.iterator().forEach {
                    it.x += ev.rawX - mPosX
                    it.y += ev.rawY - mPosY
                    mPosX = ev.rawX
                    mPosY = ev.rawY
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