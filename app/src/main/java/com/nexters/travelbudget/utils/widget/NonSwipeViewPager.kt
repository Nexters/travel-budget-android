package com.nexters.travelbudget.utils.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager
import java.lang.Exception

/**
 * 스와이프를 할 수 없는 뷰페이저
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.12
 */
class NonSwipeViewPager constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {

    init {
        setMyScroller()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return false
    }

    private fun setMyScroller() {
        try {
            val viewpager = ViewPager::class.java
            viewpager.getDeclaredField("mScroller").run {
                isAccessible = true
                set(this@NonSwipeViewPager, MyScroller(context))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class MyScroller internal constructor(context: Context) :
        Scroller(context, DecelerateInterpolator()) {

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, 500)
        }
    }
}