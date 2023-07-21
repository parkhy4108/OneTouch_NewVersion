package com.dev_musashi.onetouch.presentation.camera

import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap

sealed interface CAMERAUIEvent {

    object BoardClick : CAMERAUIEvent
    object FlashClick : CAMERAUIEvent
    data class TakePictureButton(val captureImg: ImageBitmap, val pictureImg: ImageProxy) : CAMERAUIEvent

}