package com.dev_musashi.onetouch.presentation.home

import com.dev_musashi.onetouch.domain.model.Title

sealed interface HOMEUIEvent {
    object AddTable: HOMEUIEvent
    object SaveTable: HOMEUIEvent
    object DeleteTable: HOMEUIEvent
    object ShowAddDialog: HOMEUIEvent
    object HideAddDialog: HOMEUIEvent
    object HideDelDialog: HOMEUIEvent
    object OpenGallery: HOMEUIEvent
    data class OpenCamera(val open : (String) -> Unit): HOMEUIEvent
    data class ShowDelDialog(val btn: Title): HOMEUIEvent
    data class SetTitle(val title: String): HOMEUIEvent
    data class SetName(val name: String): HOMEUIEvent
    data class SetSpecies(val species: String): HOMEUIEvent
    data class SetLocation(val location: String): HOMEUIEvent
    data class SetDate(val date: String): HOMEUIEvent
    data class SetNote(val note: String): HOMEUIEvent
    data class SetNameContent(val nameContent: String): HOMEUIEvent
    data class SetSpeciesContent(val speciesContent: String): HOMEUIEvent
    data class SetLocationContent(val locationContent: String): HOMEUIEvent
    data class SetDateContent(val dateContent: String): HOMEUIEvent
    data class SetNoteContent(val noteContent: String): HOMEUIEvent
    data class ClickTitleBtn(val btn: Title): HOMEUIEvent

}