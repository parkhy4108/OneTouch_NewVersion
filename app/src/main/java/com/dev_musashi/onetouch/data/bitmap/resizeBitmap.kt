package com.dev_musashi.onetouch.data.bitmap

import android.graphics.Bitmap

fun resizeBitmap(bitmap: Bitmap, newWidth: Int, newHeight: Int): Bitmap {
    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
}