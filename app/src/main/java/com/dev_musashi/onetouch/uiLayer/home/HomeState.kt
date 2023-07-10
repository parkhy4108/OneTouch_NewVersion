package com.dev_musashi.onetouch.uiLayer.home

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.focus.FocusRequester
import com.dev_musashi.onetouch.domain.model.Table

data class HomeState(

    val history: List<String> = emptyList(),

    val openTitleDialog: Boolean = false,
    val openDeleteTitleDialog: Boolean = false,

    val addLoading: Boolean = false,

    val tableList: List<Table> = emptyList(),
    val isSelectedState: MutableMap<Int, Boolean> = mutableStateMapOf(),

    val table: Table? = null,

    val title: String = "",

    val name: String = "", val nameContent: String = "",

    val species: String = "", val speciesContent: String = "",

    val location: String = "", val locationContent: String = "",

    val date: String = "", val dateContent: String = "",

    val empty: String = "", val emptyContent: String = "",

    val focusRequester: FocusRequester = FocusRequester()
    )
