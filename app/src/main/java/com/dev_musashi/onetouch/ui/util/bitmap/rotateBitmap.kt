package com.dev_musashi.onetouch.ui.util.bitmap

import android.graphics.Bitmap
import android.graphics.Matrix

fun rotateBitmap(bitmap: Bitmap, angle: Float): Bitmap {
    val matrix = Matrix()
    matrix.setRotate(angle)
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}