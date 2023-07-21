package com.dev_musashi.onetouch.presentation.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.focus.FocusRequester
import com.dev_musashi.onetouch.domain.model.History
import com.dev_musashi.onetouch.domain.model.Title

data class HomeState(
    val titleBtnList: List<Title> = emptyList(),
    val currentBtn: Title? = null,
    val title: String = "",
    val name: String = "",
    val species: String = "",
    val location: String = "",
    val date: String = "",
    val note: String = "",
    val isSelected: MutableMap<Int, Boolean> = mutableStateMapOf(),
    val isAddingTable: Boolean = false,
    val isDeletingTable: Boolean = false,
    val history: List<History> = emptyList(),
    val focusRequester: FocusRequester = FocusRequester()
)
