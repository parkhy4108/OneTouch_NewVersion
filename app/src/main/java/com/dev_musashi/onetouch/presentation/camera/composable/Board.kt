package com.dev_musashi.onetouch.presentation.camera.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.dev_musashi.onetouch.R
import com.dev_musashi.onetouch.presentation.camera.CAMERAUIEvent
import com.dev_musashi.onetouch.presentation.camera.CameraState

@Composable
fun Board(
    modifier: Modifier,
    state: CameraState,
    onEvent: (CAMERAUIEvent) -> Unit
) {
    Column(
        modifier = modifier
            .width(160.dp)
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
        TableRow(modifier = Modifier.weight(0.2f), titleValue = state.name, contentValue = state.nameContent)
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), titleValue = state.species, contentValue = state.speciesContent)
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), titleValue = state.location, contentValue = state.locationContent)
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), titleValue = state.date, contentValue = state.dateContent)
        Divider(color = Color.Black, thickness = 0.5.dp)
        TableRow(modifier = Modifier.weight(0.2f), titleValue = state.note, contentValue = state.noteContent)
    }
}