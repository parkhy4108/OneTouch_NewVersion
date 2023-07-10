package com.dev_musashi.onetouch.uiLayer.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.dev_musashi.onetouch.domain.model.Table

@Composable
fun ButtonList(
    list: List<Table>,
    lazyListState: LazyListState,
    isSelectedState: MutableMap<Int, Boolean>,
    focusManager: FocusManager,
    tableButtonClick: (table: Table, index: Int)->Unit,
    tableButtonLongClick: (table: Table, index: Int)->Unit,
){
    LazyRow(
        state = lazyListState,
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        contentPadding = PaddingValues(6.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        itemsIndexed(list) { index, item ->
            TableButton(
                modifier = Modifier
                    .width(80.dp)
                    .height(28.dp),
                text = item.title,
                onClick = {
                    tableButtonClick(item, index)
                    focusManager.clearFocus()
                },
                onLongClick = { tableButtonLongClick(item, index) },
                isSelectedState = isSelectedState[index] ?: false,
            )
        }
    }
}