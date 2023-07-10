package com.dev_musashi.onetouch.uiLayer.home.composable

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.uiLayer.home.HomeState
import com.dev_musashi.onetouch.uiLayer.home.HomeViewModel
import com.dev_musashi.onetouch.uiLayer.home.composable.TableRow

@Composable
fun Table(
    name: String,
    nameChanged: (String) -> Unit,
    nameContent: String,
    nameContentChanged: (String) -> Unit,
    species: String,
    speciesChanged: (String) -> Unit,
    speciesContent: String,
    speciesContentChanged: (String) -> Unit,
    location: String,
    locationChanged: (String) -> Unit,
    locationContent: String,
    locationContentChanged: (String) -> Unit,
    date: String,
    dateChanged: (String) -> Unit,
    dateContent: String,
    dateContentChanged: (String) -> Unit,
    empty: String,
    emptyChanged: (String) -> Unit,
    emptyContent: String,
    emptyContentChanged: (String) -> Unit,
    delete1BtnClicked: ()->Unit,
    delete2BtnClicked: ()->Unit,
    delete3BtnClicked: ()->Unit,
    delete4BtnClicked: ()->Unit,
    delete5BtnClicked: ()->Unit,
    tableScrollState: ScrollState,
    focusRequester: FocusRequester
) {
    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(tableScrollState),
    ) {
        TableRow(
            value1 = name,
            value2 = nameContent,
            onValue1Change = { nameChanged(it) },
            onValue2Change = { nameContentChanged(it) },
            onButtonClicked = {},
            focusRequester = focusRequester
        )
        TableRow(
            value1 = species,
            value2 = speciesContent,
            onValue1Change = { speciesChanged(it) },
            onValue2Change = { speciesContentChanged(it) },
            onButtonClicked = {},
            focusRequester = focusRequester
        )
        TableRow(
            value1 = location,
            value2 = locationContent,
            onValue1Change = { locationChanged(it) },
            onValue2Change = { locationContentChanged(it) },
            onButtonClicked = {},
            focusRequester = focusRequester
        )
        TableRow(
            value1 = date,
            value2 = dateContent,
            onValue1Change = { dateChanged(it) },
            onValue2Change = { dateContentChanged(it) },
            onButtonClicked = {},
            focusRequester = focusRequester
        )

        TableRow(
            value1 = empty,
            value2 = emptyContent,
            onValue1Change = { emptyChanged(it) },
            onValue2Change = { emptyContentChanged(it) },
            onButtonClicked = {},
            focusRequester = focusRequester
        )
    }

}