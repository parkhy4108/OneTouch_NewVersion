package com.dev_musashi.onetouch.ui.camera

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap

sealed interface CAMERAUIEvent {

    object BoardClick : CAMERAUIEvent
    object FlashClick : CAMERAUIEvent
    data class TakePictureButton(val captureImg: ImageBitmap, val pictureImg: Bitmap) : CAMERAUIEvent

}