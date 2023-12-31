package com.dev_musashi.onetouch.presentation.camera

import android.app.Activity
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
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
    val view = LocalView.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val darkTheme = isSystemInDarkTheme()
    val cameraController = remember { LifecycleCameraController(context) }
    val captureController = rememberCaptureController()
    val mainExecutor = ContextCompat.getMainExecutor(context)
    val hasFlash = remember{ mutableStateOf(false) }
    LaunchedEffect(Unit) {
        cameraViewModel.init(buttonId)
    }

    DisposableEffect(Unit) {
        val window = (view.context as Activity).window
        if (!view.isInEditMode) {
            window.statusBarColor = Color.Black.toArgb()
            window.navigationBarColor = Color.Black.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
        onDispose {
            window.statusBarColor = if(!darkTheme) Color.Transparent.toArgb() else darkColorScheme().background.toArgb()
            window.navigationBarColor = if(!darkTheme) Color.Transparent.toArgb() else darkColorScheme().background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Box(modifier = Modifier.weight(0.8f)) {
            Capturable(
                modifier = Modifier.fillMaxSize(),
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
                Box(modifier = Modifier.matchParentSize()) {
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
        }


        Row(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(50.dp)) {
                Icon(
                    modifier = Modifier.size(50.dp),
                    painter = painterResource(id = AppImg.circle),
                    contentDescription = "pictureBtn",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { captureController.capture() },
                modifier = Modifier.size(80.dp),
                enabled = !state.doSaving
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier) {
                        Icon(
                            modifier = Modifier.size(80.dp),
                            painter = painterResource(id = AppImg.circle),
                            contentDescription = "pictureBtn",
                            tint = Color.White
                        )
                        if (state.doSaving) CircularProgressIndicator(
                            modifier = Modifier.size(82.dp),
                            strokeWidth = 3.dp,
                            color = Color.White
                        )
                    }
                }

            }
            IconButton(
                onClick = {
                    if (cameraController.torchState.value == 0) cameraController.enableTorch(true)
                    else cameraController.enableTorch(false)
                    hasFlash.value = !hasFlash.value
                }) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color.White, CircleShape)
                        .padding(3.dp),
                    tint = Color.White,
                    painter = if (hasFlash.value) painterResource(id = AppImg.flash_on)
                    else painterResource(id = AppImg.flash_off),
                    contentDescription = "flashImg"
                )
            }
        }
    }
}