package com.dev_musashi.onetouch.presentation.home.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.onetouch.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableRow(
    modifier: Modifier,
    focusManager: FocusManager,
    value1: String,
    value2: String,
    onEvent1: (String) -> Unit,
    onEvent2: (String) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Text(
//            text = text,
//            modifier = Modifier
//                .weight(0.2f)
//                .height(40.dp)
//                .wrapContentHeight(Alignment.CenterVertically),
//            textAlign = TextAlign.Center,
//            color = Color.Black
//        )
        BasicTextField(
            modifier = Modifier
                .weight(0.2f)
                .height(40.dp),
            value = value1,
            onValueChange = { onEvent1(it) },
            textStyle = TextStyle.Default.copy(fontSize = 12.sp, textAlign = TextAlign.Center),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(4.dp, 0.dp, 0.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    innerTextField()
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        )
        BasicTextField(
            modifier = Modifier
                .weight(0.7f)
                .height(40.dp),
            value = value2,
            onValueChange = { onEvent2(it) },
            textStyle = TextStyle.Default.copy(fontSize = 12.sp),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .padding(4.dp, 0.dp, 0.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    innerTextField()
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        Icon(
            modifier = Modifier
                .weight(0.1f)
                .clickable { onEvent2("") },
            painter = painterResource(id = R.drawable.ic_cancel),
            contentDescription = null,
            tint = if (isSystemInDarkTheme()) Color.DarkGray else Color.LightGray
        )
    }
}