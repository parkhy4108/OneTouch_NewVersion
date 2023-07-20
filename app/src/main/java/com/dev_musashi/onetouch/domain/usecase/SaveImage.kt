package com.dev_musashi.onetouch.domain.usecase

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import com.dev_musashi.onetouch.domain.gallery.Gallery
import com.dev_musashi.onetouch.domain.repository.Repository
import javax.inject.Inject

class SaveImage @Inject constructor(
    private val gallery: Gallery
) {
    suspend operator fun invoke(
        captureImage: ImageBitmap,
        pictureImg: Bitmap,
        onError: (Throwable?) -> Unit
    ) {
        return gallery.saveImage(captureImage, pictureImg, onError)
    }
}