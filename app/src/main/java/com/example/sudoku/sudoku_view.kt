package com.example.sudoku

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

@Suppress("UNREACHABLE_CODE")
class SudokuView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var size = 9
    private var sqrtsize=3

    private var cellSizePixels = 0F

    private var selectedRow = -1
    private var selectedCol = -1

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
    private val selectPaint = Paint().apply{
        style= Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#6ead3a")
    }
    private val conflictPaint = Paint().apply{
        style= Paint.Style.FILL_AND_STROKE
        color = Color.parseColor("#efedef")
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val sizePixels: Int = Math.min(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(sizePixels, sizePixels)
    }
    private fun fillCell(canvas: Canvas, r:Int, c:Int, paint:Paint){
        canvas.drawRect(r*cellSizePixels, c*cellSizePixels, (r+1)*cellSizePixels, (c+1)*cellSizePixels, paint)
    }
    private fun fillCells(canvas : Canvas){
        if(selectedRow == -1 || selectedCol==-1) return
        for(r in 0..size){
            for(c in 0..size){
                if(r == selectedRow && c == selectedCol){
                    fillCell(canvas, r, c, selectPaint)
                }
                else if(r == selectedRow || c == selectedCol){
                    fillCell(canvas, r, c, conflictPaint)
                }
                else if(r/sqrtsize == selectedRow/sqrtsize && c/sqrtsize == selectedCol/sqrtsize){
                    fillCell(canvas, r, c, conflictPaint)
                }
            }
        }
    }
    override fun onDraw(canvas: Canvas){
        cellSizePixels=(width/size).toFloat()
        fillCells(canvas)
        drawLines(canvas)
    }
    private fun drawLines(canvas:Canvas){
        canvas.drawRect(0F, 0F, width.toFloat(), height.toFloat(), thickLinePaint)
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

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                handleTouch(event.x, event.y)
                return true
            }
            else -> return false
        }
    }
    private fun handleTouch(x: Float, y: Float) {
        selectedRow = (x/ cellSizePixels).toInt()
        selectedCol = (y/ cellSizePixels).toInt()
        invalidate()
    }

}