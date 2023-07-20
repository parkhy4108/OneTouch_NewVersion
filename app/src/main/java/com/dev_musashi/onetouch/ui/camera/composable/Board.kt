package com.dev_musashi.onetouch.ui.camera.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dev_musashi.onetouch.R
import com.dev_musashi.onetouch.ui.camera.CAMERAUIEvent
import com.dev_musashi.onetouch.ui.camera.CameraState

@Composable
fun Board(
    modifier: Modifier,
    state: CameraState,
    onEvent: (CAMERAUIEvent) -> Unit
) {
    Column(
        modifier = modifier
            .width(150.dp)
            .height(100.dp)
            .border(BorderStroke(1.dp, Color.Black), shape = RectangleShape)
            .padding(1.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onEvent(CAMERAUIEvent.BoardClick) }
                )
            }
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Name, value = state.name)
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Species, value = state.species)
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Location, value = state.location)
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Date, value = state.date)
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Note, value = state.note)
    }
}

@Preview(showBackground = true)
@Composable
fun BoardPreview(){
    Row(
        modifier = Modifier
            .background(Color.White)
            .width(100.dp)
            .height(150.dp)
            .border(BorderStroke(1.dp, Color.Black), shape = RectangleShape)
            .padding(1.dp)
        ,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Name, value = "state.name")
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Species, value = "state.species")
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Location, value = "state.location")
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Date, value = "state.date")
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), text = R.string.Note, value = "state.note")
    }
}