package com.dev_musashi.onetouch.uiLayer.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.onetouch.R

@Composable
fun TableRow(
    value1: String,
    value2: String,
    onValue1Change: (String) -> Unit,
    onValue2Change: (String) -> Unit,
    onButtonClicked: () -> Unit,
    focusRequester: FocusRequester
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(0.2f)
                .height(40.dp)
                .focusRequester(focusRequester),
            value = value1,
            onValueChange = { onValue1Change(it) },
            textStyle = TextStyle.Default.copy(
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(color = Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    innerTextField()
                }
            }
        )
        BasicTextField(
            modifier = Modifier
                .weight(0.7f)
                .height(40.dp)
                .focusRequester(focusRequester),
            value = value2,
            onValueChange = { onValue2Change(it) },
            textStyle = TextStyle.Default.copy(
                fontSize = 12.sp, color = Color.Black
            ),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .padding(4.dp, 0.dp, 0.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    innerTextField()
                }
            }
        )
//        TextField(
//            modifier = Modifier
//                .align(Alignment.CenterVertically)
//                .weight(0.2f)
//                .height(35.dp),
//            value = value1,
//            onValueChange = { onValue1Change(it) },
//            textStyle = TextStyle.Default.copy(fontSize = 6.sp, color = Color.Black)
//        )

        Icon(
            modifier = Modifier
                .weight(0.1f)
                .clickable { },
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = null
        )
    }
}