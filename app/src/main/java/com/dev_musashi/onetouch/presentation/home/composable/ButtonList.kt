package com.dev_musashi.onetouch.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.dev_musashi.onetouch.domain.model.Title
import com.dev_musashi.onetouch.presentation.home.HOMEUIEvent

@Composable
fun ButtonList(
    list: List<Title>,
    lazyListState: LazyListState,
    isSelectedState: MutableMap<Int, Boolean>,
    focusManager: FocusManager,
    onEvent: (HOMEUIEvent) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.spacedBy(17.dp),
        verticalAlignment = Alignment.CenterVertically,
        state = lazyListState
    ) {
        items(items = list, key = { item: Title -> item.id }) { item ->
            TitleButton(
                modifier = Modifier
                    .width(80.dp)
                    .height(30.dp),
                text = item.title,
                onClick = {
                    onEvent(HOMEUIEvent.ClickTitleBtn(item))
                    focusManager.clearFocus()
                },
                onLongClick = { onEvent(HOMEUIEvent.ShowDelDialog(item)) },
                isSelectedState = isSelectedState[item.id] ?: false
            )
        }
    }
}