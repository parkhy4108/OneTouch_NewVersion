package com.dev_musashi.onetouch.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    isSelectedState: Boolean,
) {
    val backgroundColor = if (isSelectedState) Color.Blue else Color.DarkGray
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(color = backgroundColor)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = { onLongClick() }
                )
            }
            ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }

}