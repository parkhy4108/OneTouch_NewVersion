package com.dev_musashi.onetouch.presentation.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.dev_musashi.onetouch.presentation.home.HOMEUIEvent
import com.dev_musashi.onetouch.presentation.home.HomeState
import com.dev_musashi.onetouch.R.string as AppText

@Composable
fun AddDialog(
    state: HomeState,
    onEvent: (HOMEUIEvent) -> Unit
) {
    Dialog(onDismissRequest = { onEvent(HOMEUIEvent.HideAddDialog) }) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(180.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.White)
                .padding(15.dp, 20.dp, 15.dp, 10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = AppText.AddDialogTitle),
                    modifier = Modifier.padding(8.dp, 3.dp, 0.dp, 0.dp)
                )
                TextField(
                    modifier = Modifier.padding(8.dp, 0.dp),
                    value = state.title,
                    onValueChange = { onEvent(HOMEUIEvent.SetTitle(it)) },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                    placeholder = { Text(text = stringResource(id = AppText.TitlePlaceHolder)) }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { onEvent(HOMEUIEvent.HideAddDialog) },
                        contentPadding = PaddingValues(0.dp),
                        content = { Text(text = stringResource(id = AppText.Cancel)) }
                    )
                    TextButton(
                        onClick = { onEvent(HOMEUIEvent.AddTable) },
                        contentPadding = PaddingValues(0.dp),
                        enabled = state.title.isNotBlank(),
                        content = { Text(text = stringResource(id = AppText.Okay)) }
                    )
                }
            }
        }
    }
}