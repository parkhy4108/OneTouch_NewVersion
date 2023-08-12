package com.dev_musashi.onetouch.presentation.camera.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TableRow(
    modifier: Modifier,
    titleValue: String,
    contentValue: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.3f),
            text = titleValue,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.5.dp),
            color = Color.Black
        )
        Text(
            modifier = Modifier
                .weight(0.7f)
                .padding(5.dp, 0.dp, 0.dp, 0.dp),
            text = contentValue,
            fontSize = 12.sp,
            color = Color.Black
        )
    }
}