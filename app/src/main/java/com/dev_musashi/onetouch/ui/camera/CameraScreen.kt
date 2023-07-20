package com.dev_musashi.onetouch.ui.camera

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.onetouch.ui.camera.composable.Board
import com.dev_musashi.onetouch.ui.camera.composable.PreviewView
import com.dev_musashi.onetouch.ui.util.snackBar.SnackBarManager
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import androidx.compose.ui.graphics.Color as ComposeColor
import com.dev_musashi.onetouch.R.string as AppText

@Composable
fun CameraScreen(
    buttonId: Int,
    cameraViewModel: CameraViewModel = hiltViewModel()
) {

    val state by cameraViewModel.state
    val rotationState = cameraViewModel.rotationState.collectAsState()
    val onEvent = cameraViewModel::onEvent
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraController = remember { LifecycleCameraController(context) }
    val captureController = rememberCaptureController()
    val mainExecutor = ContextCompat.getMainExecutor(context)

    LaunchedEffect(Unit) {
        cameraViewModel.init(buttonId)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Capturable(
            modifier = Modifier.weight(0.8f),
            controller = captureController,
            onCaptured = { capture, error ->
                if (capture != null) {
                    cameraController.takePicture(
                        mainExecutor,
                        object : ImageCapture.OnImageCapturedCallback() {
                            override fun onCaptureSuccess(picture: ImageProxy) {
                                onEvent(
                                    CAMERAUIEvent.TakePictureButton(
                                        captureImg = capture,
                                        pictureImg = picture.toBitmap()
                                    )
                                )
                            }

                            override fun onError(exception: ImageCaptureException) {
                                super.onError(exception)
                            }
                        }
                    )

                }
                if (error != null) {
                    SnackBarManager.showMessage(AppText.unKnownError)
                }
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                val align =
                    if (state.align == "TopStart") Alignment.TopStart
                    else Alignment.BottomStart

                PreviewView(
                    lifecycleOwner = lifecycleOwner,
                    cameraController = cameraController
                )
                Box(
                    modifier = Modifier
                        .rotate(90f)
                        .align(align)
                ) {
                    Board(
                        modifier = Modifier,
                        state = state,
                        onEvent = onEvent
                    )
                }
            }
        }


        Box(
            modifier = Modifier
                .weight(0.2f)
                .background(ComposeColor.Black)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        captureController.capture()
                    }
                ) {
                    Text("촬영")
                }
            }
        }
    }

}



