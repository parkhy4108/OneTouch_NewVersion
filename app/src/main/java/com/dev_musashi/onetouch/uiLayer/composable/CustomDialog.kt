package com.dev_musashi.onetouch.uiLayer.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    title: Int,
    content: @Composable () -> Unit,
    onDismissRequest: () -> Unit,
    onAcceptRequest: () -> Unit,
    acceptBtnEnabled: Boolean,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(18.dp))
                .background(Color.White)
                .padding(10.dp, 15.dp, 10.dp, 10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(text = "서식명을 입력해주세요", modifier = Modifier.padding(8.dp,0.dp))
                content()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "취소")
                    }
                    TextButton(
                        onClick = { onAcceptRequest() },
                        contentPadding = PaddingValues(0.dp),
                        enabled = acceptBtnEnabled
                    ) {
                        Text(text = "확인")
                    }
                }
            }
        }

    }
}