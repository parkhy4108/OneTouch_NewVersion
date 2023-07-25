package com.dev_musashi.onetouch.presentation.home.composable

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.onetouch.presentation.theme.Purple40

@Composable
fun TitleButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    isSelectedState: Boolean,
) {
    val backgroundColor = if (isSelectedState) Purple40 else Color.LightGray
    val textColor = if (isSelectedState) Color.White else Color.DarkGray
    Card(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onClick() },
                    onLongPress = { onLongClick() }
                )
            },
        colors = CardDefaults.cardColors(backgroundColor),
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(2.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                color = textColor,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}