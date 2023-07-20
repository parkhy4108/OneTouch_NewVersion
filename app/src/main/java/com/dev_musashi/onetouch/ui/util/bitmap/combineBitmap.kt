package com.dev_musashi.onetouch.ui.util.bitmap

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.max

fun combineBitmap(picture: Bitmap, capture: Bitmap): Bitmap {

    val resultWidth = max(picture.width, capture.width)
    val resultHeight = max(picture.height, capture.height)

    val resultBitmap = Bitmap.createBitmap(resultWidth, resultHeight, picture.config)
    val canvas = Canvas(resultBitmap)

    canvas.drawBitmap(picture, 0f, 0f, Paint())
    canvas.drawBitmap(capture, 0f, 0f, Paint())

    return resultBitmap
}