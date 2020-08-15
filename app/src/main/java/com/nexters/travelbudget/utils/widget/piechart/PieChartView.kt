package com.nexters.travelbudget.utils.widget.piechart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.nexters.travelbudget.R
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var dataList = ArrayList<PieData>()
    private var anglePointList = ArrayList<AnglePoint>()
    private val colorList by lazy {
        ArrayList<Int>().apply {
            add(resources.getColor(R.color.fill_blue, null))
            add(resources.getColor(R.color.fill_blue_2, null))
            add(resources.getColor(R.color.fill_blue_3, null))
            add(resources.getColor(R.color.fill_blue_4, null))
            add(resources.getColor(R.color.fill_blue_5, null))
            add(resources.getColor(R.color.fill_blue_6, null))
            add(resources.getColor(R.color.fill_blue_7, null))
            add(resources.getColor(R.color.fill_blue_8, null))
        }
    }

    private var onPieTouchListener: OnPieTouchListener? = null

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if (heightMode != MeasureSpec.EXACTLY) {
            height = width
        }

        if (widthMode != MeasureSpec.EXACTLY) {
            width = height
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var totalData = 0.0f
        for (pie in dataList) {
            totalData += pie.value
        }

        var anglePointer = 0f

        anglePointList.clear()
        var colorIdx = 0

        for (pie in dataList) {
            var sweepAngle = pie.value / totalData * ANGLE

            canvas?.drawArc(
                RectF(measuredWidth, measuredHeight),
                anglePointer - 90,
                sweepAngle,
                false,
                Paint().apply {
                    color = colorList[colorIdx++]
                    strokeWidth = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        50f,
                        context.resources.displayMetrics
                    )
                    style = Paint.Style.STROKE
                }
            )
            anglePointList.add(AnglePoint(anglePointer, anglePointer + sweepAngle))
            anglePointer += sweepAngle
        }
    }

    fun getPieDataList(): List<PieData> {
        return dataList
    }

    fun setDataList(list: ArrayList<PieData>) {
        dataList = list
        invalidate()
    }

    fun addData(data: PieData) {
        dataList.add(data)
        invalidate()
    }

    private fun getIndex(angle: Double): Int {
        var idx = 0
        for (anglePoint in anglePointList) {
            if (anglePoint.from < angle && anglePoint.to > angle) {
                break
            }
            idx++
        }

        return idx
    }

    private fun RectF(width: Int, height: Int): RectF {
        return RectF(width * 0.1f, height * 0.1f, width * 0.9f, height * 0.9f)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val targetX = event?.x ?: 0f
        val targetY = event?.y ?: 0f

        if (isOutSide(targetX, targetY)) {
            val angle = getAngle(targetY, targetX)

            onPieTouchListener?.onPieTouched(getIndex(angle))
        }

        return super.onTouchEvent(event)
    }

    fun setOnPieTouchListener(listener: (Int) -> Unit) {
        onPieTouchListener = object : OnPieTouchListener {
            override fun onPieTouched(index: Int) {
                listener(index)
            }
        }
    }

    private fun getAngle(y: Float, x: Float): Double {
        val centerX = measuredWidth / 2.0
        val centerY = measuredHeight / 2.0

        val r = sqrt(
            (x - centerX).pow(2.0) + (y - centerY).pow(2.0)
        )
        val xLen = abs(x - centerX)
        val sinValue = xLen / r
        var angle = asin(sinValue) * 360 / (Math.PI * 2)

        if (y >= centerY && x > centerX) {
            angle = 90 - angle
            angle += 90.0
        } else if (y > centerY && x <= centerX) {
            angle += 180.0
        } else if (y <= centerY && x < centerX) {
            angle = 90 - angle
            angle += 270.0
        }

        return angle
    }

    private fun isOutSide(x: Float, y: Float): Boolean {
        val centerX = measuredWidth / 2.0f
        val centerY = measuredHeight / 2.0f

        val r = (measuredWidth - centerX) * 0.85f

        val insideR = abs(r - centerX)

        val dist = sqrt((y - centerY).pow(2) + (x - centerX).pow(2))

        return dist >= insideR
    }

    companion object {
        private const val ANGLE = 360f
    }

    private data class AnglePoint (
        val from: Float,
        val to: Float
    )

    interface OnPieTouchListener {
        fun onPieTouched(index: Int)
    }
}