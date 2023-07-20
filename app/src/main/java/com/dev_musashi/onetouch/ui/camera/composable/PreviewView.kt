package com.dev_musashi.onetouch.ui.camera.composable

import android.graphics.Color
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner

@Composable
fun PreviewView(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner,
    cameraController: LifecycleCameraController
) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            androidx.camera.view.PreviewView(context).apply {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundColor(Color.BLACK)
                scaleType = androidx.camera.view.PreviewView.ScaleType.FILL_START
            }.also { previewView ->
                previewView.controller = cameraController
                cameraController.bindToLifecycle(lifecycleOwner)
            }
        }
    )
}