package com.dev_musashi.onetouch.ui.composable

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dev_musashi.onetouch.ui.home.HOMEUIEvent
import com.dev_musashi.onetouch.R.string as AppText

@Composable
fun DeleteDialog(
    onEvent: (HOMEUIEvent) -> Unit
) {
    Dialog(onDismissRequest = { onEvent(HOMEUIEvent.HideDelDialog) }) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.White)
                .padding(15.dp, 20.dp, 15.dp, 15.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(id = AppText.DeleteTable), modifier = Modifier.padding(8.dp, 0.dp))
                Text(text = stringResource(id = AppText.DelDialogText), modifier = Modifier.padding(8.dp, 0.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { onEvent(HOMEUIEvent.HideDelDialog) },
                        contentPadding = PaddingValues(0.dp),
                        content = { Text(text = stringResource(id = AppText.Cancel)) }
                    )
                    TextButton(
                        onClick = { onEvent(HOMEUIEvent.DeleteTable) },
                        contentPadding = PaddingValues(0.dp),
                        enabled = true,
                        content = { Text(text = stringResource(id = AppText.Okay)) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DelDialogPreview(){
    Dialog(onDismissRequest = { }) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color.White)
                .padding(15.dp, 20.dp, 15.dp, 15.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(text = stringResource(id = AppText.DeleteTable), modifier = Modifier.padding(8.dp, 0.dp))
                Text(text = stringResource(id = AppText.DelDialogText), modifier = Modifier.padding(8.dp, 0.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {  },
                        contentPadding = PaddingValues(0.dp),
                        content = { Text(text = stringResource(id = AppText.Cancel)) }
                    )
                    TextButton(
                        onClick = {  },
                        contentPadding = PaddingValues(0.dp),
                        enabled = true,
                        content = { Text(text = stringResource(id = AppText.Okay)) }
                    )
                }
            }
        }
    }
}



