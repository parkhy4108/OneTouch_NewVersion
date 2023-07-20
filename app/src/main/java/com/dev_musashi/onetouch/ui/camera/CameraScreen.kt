package com.dev_musashi.onetouch.ui.camera

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.onetouch.R
import com.dev_musashi.onetouch.ui.camera.composable.Board
import com.dev_musashi.onetouch.ui.camera.composable.CameraView
import com.dev_musashi.onetouch.ui.camera.composable.TableRow
import com.dev_musashi.onetouch.ui.composable.vertical
import com.dev_musashi.onetouch.ui.util.snackBar.SnackBarManager
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import androidx.compose.ui.graphics.Color as ComposeColor
import com.dev_musashi.onetouch.R.string as AppText
import com.dev_musashi.onetouch.R.drawable as AppImg

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

                CameraView(
                    lifecycleOwner = lifecycleOwner,
                    cameraController = cameraController
                )

                Box(
                    modifier = Modifier
                        .align(align)
                        .vertical()
                        .rotate(90f)

                ) {
                    Board(
                        modifier = Modifier,
                        state = state,
                        onEvent = onEvent
                    )
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(51.dp),
                    painter = painterResource(id = AppImg.circle),
                    contentDescription = "pictureBtn",
                    tint = Color.White
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = AppImg.circle),
                    contentDescription = "pictureBtn",
                    tint = Color.White
                )
            }
            val flashImg =
                if (state.flash) painterResource(id = AppImg.flash_on)
                else painterResource(id = AppImg.flash_off)
            IconButton(
                onClick = {
                    onEvent(CAMERAUIEvent.FlashClick)
                    cameraController.enableTorch(state.flash)
                    println("torch state: ${cameraController.torchState.value}")
                },
            ) {
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


@Preview(showBackground = true)
@Composable
fun CameraPreview() {
    var flash by remember {
        mutableStateOf(false)
    }

    var alignState by remember {
        mutableStateOf("BottomEnd")
    }
    val align = if (alignState == "BottomEnd") Alignment.BottomEnd else Alignment.TopEnd
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(0.8f)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ComposeColor.Black)
            )

            Box(
                modifier = Modifier
                    .align(align)
                    .vertical()
                    .rotate(270f)
            ) {
                Column(
                    modifier = Modifier
                        .width(150.dp)
                        .height(100.dp)
                        .border(BorderStroke(1.dp, Color.Black), shape = RectangleShape)
                        .padding(1.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    alignState =
                                        if (alignState == "BottomEnd") "TopEnd" else "BottomEnd"
                                }
                            )
                        }
                        .background(Color.White),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    TableRow(
                        modifier = Modifier.weight(0.2f),
                        text = R.string.Name,
                        value = "state.name"
                    )
                    Divider(color = Color.Black, thickness = 0.5.dp)
                    TableRow(
                        modifier = Modifier.weight(0.2f),
                        text = R.string.Species,
                        value = "state.species"
                    )
                    Divider(color = Color.Black, thickness = 0.5.dp)
                    TableRow(
                        modifier = Modifier.weight(0.2f),
                        text = R.string.Location,
                        value = "state.location"
                    )
                    Divider(color = Color.Black, thickness = 0.5.dp)
                    TableRow(
                        modifier = Modifier.weight(0.2f),
                        text = R.string.Date,
                        value = "state.date"
                    )
                    Divider(color = Color.Black, thickness = 0.5.dp)
                    TableRow(
                        modifier = Modifier.weight(0.2f),
                        text = R.string.Note,
                        value = "state.note"
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .background(Color.Black),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    modifier = Modifier.size(51.dp),
                    painter = painterResource(id = AppImg.circle),
                    contentDescription = "pictureBtn",
                    tint = Color.White
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    modifier = Modifier.size(80.dp),
                    painter = painterResource(id = AppImg.circle),
                    contentDescription = "pictureBtn",
                    tint = Color.White
                )
            }
            val flashImg =
                if (flash) painterResource(id = AppImg.flash_on)
                else painterResource(id = AppImg.flash_off)
            IconButton(onClick = { flash = !flash }) {
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





