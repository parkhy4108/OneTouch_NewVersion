package com.dev_musashi.onetouch.uiLayer.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.onetouch.R

@Composable
fun ExpandedTopBar(
    state: HomeState,
    onEvent: (UIEvent) -> Unit
){
    //One Touch Title & Gallery Button
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stringResource(id = R.string.OneTouch), color = Color.Black, fontSize = 10.sp)
        Icon(
            modifier = Modifier.clickable { onEvent(UIEvent.OpenGallery) },
            painter = painterResource(id = R.drawable.ic_photo),
            contentDescription = "gallery"
        )
    }

    //History
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color.Black)
    ) {

    }
}