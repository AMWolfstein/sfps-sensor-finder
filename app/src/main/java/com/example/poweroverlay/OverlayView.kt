package com.example.poweroverlay

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class OverlayView(context: Context) : View(context) {
    private val paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
        alpha = 128 // semi-transparent
    }

    private val x = 1040f
    private val y = 1014f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(x, y, 20f, paint)
    }
}
