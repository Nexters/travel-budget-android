package com.nexters.travelbudget.utils.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.nexters.travelbudget.R

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val typed by lazy {
        context.obtainStyledAttributes(R.styleable.CircleView)
    }

    private val paint by lazy {
        Paint().apply {
            color = context.getColor(R.color.fill_blue)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

//        canvas?.drawOval(width * 0.1f, height * 0.1f, width * 0.9f, height * 0.9f, paint)
        canvas?.drawOval(0f, 0f, width.toFloat(), height.toFloat(), paint)
    }

    fun setCircleColor(@ColorInt color: Int) {
        paint.color = color
        invalidate()
    }
}