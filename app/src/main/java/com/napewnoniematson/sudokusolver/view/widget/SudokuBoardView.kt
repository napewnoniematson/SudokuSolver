package com.napewnoniematson.sudokusolver.view.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SudokuBoardView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var sqrtSize = 3
    private var size = 9

    private var cellSizePixels = 0f

    private var selectedRow = -1
    private var selectedCol = -1

    private val thickLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 6f
    }

    private val thinLinePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4f
    }

    private val selectedCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#6c5b7b")
    }

    private val conflictingCellPaint = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#c06c84")
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
        fillCells(canvas)
        drawLines(canvas)
    }

    private fun fillCells(canvas: Canvas?) {
        if (selectedRow == -1 || selectedCol == -1) return
        for (r in 0..size) {
            for (c in 0..size) {
                if (r == selectedRow && c == selectedCol) {
                    fillCell(canvas, r, c, selectedCellPaint)
                } else if (r == selectedRow || c == selectedCol) {
                    fillCell(canvas, r, c, conflictingCellPaint)
                } else if (r / sqrtSize == selectedRow / sqrtSize && c / sqrtSize == selectedCol / sqrtSize) {
                    fillCell(canvas, r, c, conflictingCellPaint)
                }
            }
        }
    }

    private fun fillCell(canvas: Canvas?, row: Int, col: Int, paint: Paint) {
        canvas?.drawRect(
            col * cellSizePixels,
            row * cellSizePixels,
            (col + 1) * cellSizePixels,
            (row + 1) * cellSizePixels,
            paint
        )
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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                handleTouchEvent(event.x, event.y)
                true
            }
            else -> false
        }
    }

    private fun handleTouchEvent(x: Float, y: Float) {
        selectedRow = (y / cellSizePixels).toInt()
        selectedCol = (x / cellSizePixels).toInt()
        invalidate()
    }

}