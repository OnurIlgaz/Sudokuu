package com.example.sudoku

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

class SudokuView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var size = 9
    private var sqrtsize=3

    private var cellSizePixels = 0F

    private val thickLinePaint = Paint().apply{
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 4F
    }

    private val thinLinePaint = Paint().apply{
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 2F
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels: Int = Math.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }

    override fun onDraw(canvas: Canvas){
        cellSizePixels=(width/size).toFloat()
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)
        drawLines(canvas)
    }
    private fun drawLines(canvas:Canvas){
        for(i in 1..size){

            val paintToUse = when(i % sqrtsize){
                0->thickLinePaint
                else -> thinLinePaint
            }
            canvas.drawLine(
                i*cellSizePixels,
                0F,
                i*cellSizePixels,
                height.toFloat(),
                paintToUse
            )
            canvas.drawLine(
                0F,
                i*cellSizePixels,
                width.toFloat(),
                cellSizePixels*i,
                paintToUse
            )
        }
    }
}