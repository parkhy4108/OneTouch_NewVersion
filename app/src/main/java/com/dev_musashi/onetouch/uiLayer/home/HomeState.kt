package com.dev_musashi.onetouch.uiLayer.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.focus.FocusRequester
import com.dev_musashi.onetouch.domain.model.Table

data class HomeState(
    val tables: List<Table> = emptyList(),
    val title: String = "",
    val name: String = "",
    val species: String = "",
    val location: String = "",
    val date: String = "",
    val note: String = "",
    val isAddingTable: Boolean = false,
    val isDeletingTable: Boolean = false,
    val history: List<String> = emptyList(),
    val isSelectedState: MutableMap<Int, Boolean> = mutableStateMapOf(),
    val focusRequester: FocusRequester = FocusRequester()
    )
