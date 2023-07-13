package com.dev_musashi.onetouch.uiLayer.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.onetouch.domain.model.TitleButton
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.uiLayer.home.UIEvent

@Composable
fun ButtonList(
    list: List<TitleButton>,
    lazyListState: LazyListState,
    isSelectedState: MutableMap<Int, Boolean>,
    focusManager: FocusManager,
    onEvent: (UIEvent) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        contentPadding = PaddingValues(10.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(17.dp),
        verticalAlignment = Alignment.CenterVertically,
        state = lazyListState
    ) {
        items(items = list, key = { item: TitleButton -> item.id }) { item ->

            TableButton(
                modifier = Modifier
                    .width(80.dp)
                    .height(28.dp),
                text = item.title,
                onClick = {
                    onEvent(UIEvent.ClickTitleBtn(item))
                    focusManager.clearFocus()
                },
                onLongClick = { onEvent(UIEvent.ShowDelDialog(item)) },
                isSelectedState = isSelectedState[item.id] ?: false,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ListPreview() {

    val list = mutableListOf<Table>()

    repeat(10) {
        val table = Table(
            id = it,
            timestamp = it.toLong(),
            title = it.toString(),
            name = it.toString(),
            species = it.toString(),
            location = it.toString(),
            date = it.toString(),
            note = it.toString(),
        )
        list.add(table)
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        contentPadding = PaddingValues(10.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(17.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items = list, key = { item: Table -> item.id }) { item ->
            Button(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onLongPress = {
                            }
                        )
                    },

                onClick = {
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                contentPadding = PaddingValues(0.dp),
                elevation = ButtonDefaults.elevation(5.dp)
            ) {
                Text(
                    text = item.title,
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}


