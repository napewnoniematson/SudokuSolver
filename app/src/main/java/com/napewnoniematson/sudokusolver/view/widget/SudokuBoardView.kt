package com.napewnoniematson.sudokusolver.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var sqrtSize = 3
    private var size = 9

    private var cellSizePixels = 0f

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4f
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val sizePixels = when (haveSameSign(widthMeasureSpec, heightMeasureSpec)) {
            true -> widthMeasureSpec.coerceAtMost(heightMeasureSpec)
            false -> widthMeasureSpec.coerceAtLeast(heightMeasureSpec)
        }
        setMeasuredDimension(sizePixels, sizePixels)
    }

    private fun haveSameSign(arg1: Int, arg2: Int) = arg1 > 0 && arg2 > 0 || arg1 < 0 && arg2 < 0

    override fun onDraw(canvas: Canvas?) {
        cellSizePixels = (width / size).toFloat()
        drawLines(canvas)
    }

    private fun drawLines(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), thickLinePaint)

        for (i in 1 until size) {
            val paintToUse = when (i % sqrtSize) {
                0 -> thickLinePaint
                else -> thinLinePaint
            }
            // vertical
            canvas?.drawLine(
                i * cellSizePixels,
                0f,
                i * cellSizePixels,
                height.toFloat(),
                paintToUse
            )
            // horizontal
            canvas?.drawLine(
                0f,
                i * cellSizePixels,
                width.toFloat(),
                i * cellSizePixels,
                paintToUse
            )
        }
    }


}