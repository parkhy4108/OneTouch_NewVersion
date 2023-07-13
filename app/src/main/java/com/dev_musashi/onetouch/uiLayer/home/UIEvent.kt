package com.dev_musashi.onetouch.uiLayer.home

import com.dev_musashi.onetouch.domain.model.TitleButton

sealed interface UIEvent {
    object AddTable: UIEvent
    object SaveTable: UIEvent
    object DeleteTable: UIEvent
    object ShowAddDialog: UIEvent
    object HideAddDialog: UIEvent
    object HideDelDialog: UIEvent
    object OpenGallery: UIEvent
    object OpenCamera: UIEvent

    data class ShowDelDialog(val btn: TitleButton): UIEvent
    data class SetTitle(val title: String): UIEvent
    data class SetName(val name: String): UIEvent
    data class SetSpecies(val species: String): UIEvent
    data class SetLocation(val location: String): UIEvent
    data class SetDate(val date: String): UIEvent
    data class SetNote(val note: String): UIEvent
    data class ClickTitleBtn(val btn: TitleButton): UIEvent

}