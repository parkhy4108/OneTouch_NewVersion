package com.dev_musashi.onetouch.uiLayer.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.focus.FocusRequester
import com.dev_musashi.onetouch.domain.model.TitleButton

data class HomeState(
    val titleBtnList: List<TitleButton> = emptyList(),
    val currentBtn: TitleButton? = null,
    val title: String = "",
    val name: String = "",
    val species: String = "",
    val location: String = "",
    val date: String = "",
    val note: String = "",
    val isSelected: MutableMap<Int, Boolean> = mutableStateMapOf(),
    val isAddingTable: Boolean = false,
    val isDeletingTable: Boolean = false,
    val history: List<String> = emptyList(),
    val focusRequester: FocusRequester = FocusRequester()
)
