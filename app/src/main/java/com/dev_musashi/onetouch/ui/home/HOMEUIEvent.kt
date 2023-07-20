package com.dev_musashi.onetouch.ui.home

import com.dev_musashi.onetouch.domain.model.TitleButton

sealed interface HOMEUIEvent {
    object AddTable: HOMEUIEvent
    object SaveTable: HOMEUIEvent
    object DeleteTable: HOMEUIEvent
    object ShowAddDialog: HOMEUIEvent
    object HideAddDialog: HOMEUIEvent
    object HideDelDialog: HOMEUIEvent
    object OpenGallery: HOMEUIEvent
    data class OpenCamera(val open : (String) -> Unit): HOMEUIEvent
    data class ShowDelDialog(val btn: TitleButton): HOMEUIEvent
    data class SetTitle(val title: String): HOMEUIEvent
    data class SetName(val name: String): HOMEUIEvent
    data class SetSpecies(val species: String): HOMEUIEvent
    data class SetLocation(val location: String): HOMEUIEvent
    data class SetDate(val date: String): HOMEUIEvent
    data class SetNote(val note: String): HOMEUIEvent
    data class ClickTitleBtn(val btn: TitleButton): HOMEUIEvent

}