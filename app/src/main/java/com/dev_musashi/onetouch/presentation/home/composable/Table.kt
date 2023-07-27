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
    modifier : Modifier,
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
            text = stringResource(id = AppText.Name),
            value = state.name,
            onEvent = { onEvent(HOMEUIEvent.SetName(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            text = stringResource(id = AppText.Species),
            value = state.species,
            onEvent = { onEvent(HOMEUIEvent.SetSpecies(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            text = stringResource(id = AppText.Location),
            value = state.location,
            onEvent = { onEvent(HOMEUIEvent.SetLocation(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            text = stringResource(id = AppText.Date),
            value = state.date,
            onEvent = { onEvent(HOMEUIEvent.SetDate(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            focusManager = focusManager,
            text = stringResource(id = AppText.Note),
            value = state.note,
            onEvent = { onEvent(HOMEUIEvent.SetNote(it)) },
        )
    }

}