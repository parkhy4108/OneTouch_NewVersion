package com.dev_musashi.onetouch.domain.gallery

import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap

interface Gallery {
    suspend fun saveImage(
        captureImg: ImageBitmap,
        pictureImg: ImageProxy,
//        onError: (Throwable?) -> Unit
    ) : Throwable?

}