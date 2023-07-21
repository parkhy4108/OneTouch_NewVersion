package com.dev_musashi.onetouch.data.gallery

import android.content.Context
import android.os.Build
import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap
import com.dev_musashi.onetouch.domain.gallery.Gallery
import com.dev_musashi.onetouch.data.util.saveImageUnder29
import com.dev_musashi.onetouch.data.util.saveImageUpper29
import javax.inject.Inject

class GalleryImpl @Inject constructor(
    private val context: Context
) : Gallery {

    override suspend fun saveImage(
        captureImg: ImageBitmap,
        pictureImg: ImageProxy,
        onError: (Throwable?) -> Unit
    ) {
        try {
            if (Build.VERSION.SDK_INT < 29) {
                saveImageUnder29(
                    context,
                    captureImg = captureImg,
                    pictureImg = pictureImg.toBitmap()
                ) { exception ->
                    if(exception != null) throw exception
                }
            } else {
                saveImageUpper29(
                    context,
                    captureImg = captureImg,
                    pictureImg = pictureImg.toBitmap(),
                ) { exception ->
                    if(exception != null) throw exception
                }
            }
            onError(null)
        } catch (e: Throwable) {
            onError(e)
        }
    }

}