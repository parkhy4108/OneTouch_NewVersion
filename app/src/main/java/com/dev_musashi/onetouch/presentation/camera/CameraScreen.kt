package com.dev_musashi.onetouch.presentation.camera

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.onetouch.presentation.camera.composable.Board
import com.dev_musashi.onetouch.presentation.camera.composable.CameraView
import com.dev_musashi.onetouch.presentation.camera.composable.vertical
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarManager
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarMessage.Companion.toSnackBarMessage
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import com.dev_musashi.onetouch.R.drawable as AppImg

@Composable
fun CameraScreen(
    buttonId: Int,
    cameraViewModel: CameraViewModel = hiltViewModel()
) {

    val state by cameraViewModel.state.collectAsState()
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
                                        pictureImg = picture
                                    )
                                )
                            }

                            override fun onError(exception: ImageCaptureException) {
                                super.onError(exception)
                                SnackBarManager.showMessage(exception.toSnackBarMessage())
                            }
                        }
                    )

                }
                if (error != null) {
                    SnackBarManager.showMessage(error.toSnackBarMessage())
                }
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                CameraView(
                    lifecycleOwner = lifecycleOwner,
                    cameraController = cameraController
                )
                if (state.degree == 0f) {
                    val alignment =
                        if (state.align0f == "BottomStart") Alignment.BottomStart else Alignment.BottomEnd
                    Box(
                        modifier = Modifier.align(alignment)
                    ) {
                        Board(
                            modifier = Modifier,
                            state = state,
                            onEvent = onEvent
                        )
                    }
                }

                if (state.degree == 90f) {
                    val alignment = if (state.align90f == "TopStart") Alignment.TopStart
                    else Alignment.BottomStart
                    Box(
                        modifier = Modifier
                            .vertical()
                            .rotate(state.degree)
                            .align(alignment)
                    ) {
                        Board(
                            modifier = Modifier,
                            state = state,
                            onEvent = onEvent
                        )
                    }
                }

                if (state.degree == 270f) {
                    val alignment =
                        if (state.align270f == "BottomEnd") Alignment.BottomEnd else Alignment.TopEnd
                    Box(
                        modifier = Modifier
                            .vertical()
                            .rotate(state.degree)
                            .align(alignment)
                    ) {
                        Board(
                            modifier = Modifier,
                            state = state,
                            onEvent = onEvent
                        )
                    }
                }

            }
        }

        Row(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val flashImg =
                if (state.flash) painterResource(id = AppImg.flash_on)
                else painterResource(id = AppImg.flash_off)
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = AppImg.circle),
                    contentDescription = "pictureBtn",
                    tint = Color.White
                )
            }
            IconButton(onClick = { captureController.capture() }) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = AppImg.circle),
                    contentDescription = "pictureBtn",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                    onEvent(CAMERAUIEvent.FlashClick)
                    cameraController.enableTorch(!state.flash)
                }) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color.White, CircleShape)
                        .padding(3.dp),
                    tint = Color.White,
                    painter = flashImg,
                    contentDescription = "flashImg"
                )
            }
        }
    }

}