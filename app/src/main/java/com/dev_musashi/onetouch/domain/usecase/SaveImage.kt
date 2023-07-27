package com.dev_musashi.onetouch.domain.usecase

import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap
import com.dev_musashi.onetouch.domain.gallery.Gallery
import javax.inject.Inject

class SaveImage @Inject constructor(
    private val gallery: Gallery
) {
    suspend operator fun invoke(
        captureImage: ImageBitmap,
        pictureImg: ImageProxy,
    ) : Throwable? {
        return gallery.saveImage(captureImage, pictureImg)
    }
}