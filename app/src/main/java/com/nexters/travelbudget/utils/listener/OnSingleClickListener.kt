package com.nexters.travelbudget.utils.listener

import android.view.View
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 단일 클릭 리스너
 *
 * @author Wayne
 * @since v1.0.0 / 2020.08.14
 */
class OnSingleClickListener(private val listener: View.OnClickListener) : View.OnClickListener {
    private val canClick = AtomicBoolean(true)

    override fun onClick(v: View) {
        if (canClick.getAndSet(false)) {
            v.postDelayed({ canClick.set(true) }, INTERVAL_MILLIS)
            listener.onClick(v)
        }
    }

    companion object {
        private const val INTERVAL_MILLIS = 1000L
    }
}