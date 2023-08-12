package com.dev_musashi.onetouch.presentation.home.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.stringResource
import com.dev_musashi.onetouch.presentation.home.HOMEUIEvent
import com.dev_musashi.onetouch.presentation.home.HomeState
import com.dev_musashi.onetouch.R.string as AppText

@Composable
fun Table(
    modifier: Modifier,
    focusManager: FocusManager,
    state: HomeState,
    onEvent: (HOMEUIEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            value1 = state.name,
            value2 = state.nameContent,
            onEvent1 = { onEvent(HOMEUIEvent.SetName(it)) },
            onEvent2 = { onEvent(HOMEUIEvent.SetNameContent(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            value1 = state.species,
            value2 = state.speciesContent,
            onEvent1 = { onEvent(HOMEUIEvent.SetSpecies(it)) },
            onEvent2 = { onEvent(HOMEUIEvent.SetSpeciesContent(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            value1 = state.location,
            value2 = state.locationContent,
            onEvent1 = { onEvent(HOMEUIEvent.SetLocation(it)) },
            onEvent2 = { onEvent(HOMEUIEvent.SetLocationContent(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            value1 = state.date,
            value2 = state.dateContent,
            onEvent1 = { onEvent(HOMEUIEvent.SetDate(it)) },
            onEvent2 = { onEvent(HOMEUIEvent.SetDateContent(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            value1 = state.note,
            value2 = state.noteContent,
            onEvent1 = { onEvent(HOMEUIEvent.SetNote(it)) },
            onEvent2 = { onEvent(HOMEUIEvent.SetNoteContent(it)) },
        )
    }

}