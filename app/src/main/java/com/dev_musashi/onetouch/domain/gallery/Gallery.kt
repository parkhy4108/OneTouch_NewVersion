package com.dev_musashi.onetouch.domain.gallery

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap

interface Gallery {
    suspend fun saveImage(
        captureImg: ImageBitmap,
        pictureImg: Bitmap,
        onError: (Throwable?) -> Unit
    )
}