package com.test.points.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.test.points.R
import com.test.points.R.styleable.ChartView_axle_color
import com.test.points.R.styleable.ChartView_bg_color
import com.test.points.R.styleable.ChartView_chart_color
import com.test.points.business.models.Point

class ChartView : View {
    private var points: ArrayList<Point>? = null
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bgColor: Int = Color.GRAY
    private var axleColor: Int = Color.GREEN
    private var chartColor: Int = Color.RED

    private var axleYLine: Float = -1f
    private var axleXLine: Float = -1f

    private var zoomX = 1f
    private var zoomY = 1f

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attr: AttributeSet?) : this(context, attr, 0)

    @SuppressLint("Recycle")
    @Suppress("NAME_SHADOWING")
    constructor(context: Context, attr: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attr,
        defStyleAttr
    ) {
        attr?.let {
            val attr = context.obtainStyledAttributes(it, R.styleable.ChartView)
            bgColor = attr.getColor(ChartView_bg_color, Color.GRAY)
            axleColor = attr.getColor(ChartView_axle_color, Color.GREEN)
            chartColor = attr.getColor(ChartView_chart_color, Color.RED)
        }
    }

    override fun onMeasure(
        widthMeasureSpec: Int,
        heightMeasureSpec: Int
    ) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        this.setMeasuredDimension(width, height)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(bgColor)
        drawAxle(canvas)
        drawPoints(canvas)
    }

    fun setPoints(points: ArrayList<Point>) {
        this.points = points
        invalidate()
    }

    override fun invalidate() {
        super.invalidate()
        calculateAxle()
    }

    private fun calculateAxle() {
        points?.let {
            if (it.isNotEmpty()) {
                val minX = it[0].x
                val maxX = it[it.size - 1].x
                val minY = getMinY(it)
                val maxY = getMaxY(it)
                zoomX = width / (maxX - minX)
                zoomY = height / (maxY - minY)
                axleYLine = -minX * zoomX
                axleXLine = height - (-minY * zoomY)
            }
        }
    }

    private fun calculateX(point: Point): Float {
        return axleYLine + (point.x * zoomX)
    }

    private fun calculateY(point: Point): Float {
        return height - axleXLine + (point.y * zoomY)
    }

    private fun getMinY(list: ArrayList<Point>): Float {
        var minY = list[0].y
        list.forEach {
            if (it.y < minY) {
                minY = it.y
            }
        }
        return minY
    }

    private fun getMaxY(list: ArrayList<Point>): Float {
        var maxY = list[0].y
        list.forEach {
            if (it.y > maxY) {
                maxY = it.y
            }
        }
        return maxY
    }

    private fun drawAxle(canvas: Canvas) {
        paint.color = axleColor
        paint.strokeWidth = 2f
        if (axleYLine >= 0) {
            canvas.drawLine(axleYLine, 0f, axleYLine, height.toFloat(), paint)
        }
        if (axleXLine >= 0) {
            canvas.drawLine(0f, axleXLine, width.toFloat(), axleXLine, paint)
        }
    }

    private fun drawPoints(canvas: Canvas) {
        paint.color = chartColor
        paint.strokeWidth = 3f
        points?.let {
            for (i in 0 until it.size - 1) {
                canvas.drawLine(
                    calculateX(it[i]),
                    calculateY(it[i]),
                    calculateX(it[i + 1]),
                    calculateY(it[i + 1]),
                    paint
                )
            }
        }
    }
}
