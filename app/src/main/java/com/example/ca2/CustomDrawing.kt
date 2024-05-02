package com.example.ca2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation

class CustomDrawing(context: Context?) : View(context) {

    private var rotationAngle = 0f
    private lateinit var fanPaint: Paint
    private lateinit var bladePaint: Paint

    init {
        init()
        startFanAnimation()
    }

    private fun init() {
        fanPaint = Paint()
        fanPaint.color = Color.BLUE

        bladePaint = Paint()
        bladePaint.color = Color.YELLOW
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw fan body
        val centerX = width / 2.toFloat()
        val centerY = height / 2.toFloat()
        val fanRadius = 100f
        canvas.drawCircle(centerX, centerY, fanRadius, fanPaint)

        // Draw fan blades
        val bladeRect = RectF(centerX - 10, centerY - fanRadius - 20, centerX + 10, centerY)
        for (i in 0 until 3) {
            canvas.save()
            canvas.rotate(rotationAngle + i * 120f, centerX, centerY)
            canvas.drawRect(bladeRect, bladePaint)
            canvas.restore()
        }
    }

    fun startFanAnimation() {
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.repeatCount = Animation.INFINITE
        rotateAnimation.duration = 2000
        startAnimation(rotateAnimation)
    }
}
