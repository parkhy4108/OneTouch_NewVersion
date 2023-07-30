package com.dev_musashi.onetouch.data.bitmap

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.max

fun combineBitmap(picture1: Bitmap, picture2: Bitmap): Bitmap {

    val resultWidth = max(picture1.width, picture2.width)
    val resultHeight = max(picture1.height, picture2.height)

    val resultBitmap = Bitmap.createBitmap(resultWidth, resultHeight, picture1.config)
    val canvas = Canvas(resultBitmap)

    canvas.drawBitmap(picture1, 0f, 0f, Paint())
    canvas.drawBitmap(picture2, 0f, 0f, Paint())

    return resultBitmap
}