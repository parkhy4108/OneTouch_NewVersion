package com.dev_musashi.onetouch.presentation.home.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dev_musashi.onetouch.R
import com.dev_musashi.onetouch.presentation.home.HOMEUIEvent
import com.dev_musashi.onetouch.presentation.home.HomeState
import com.dev_musashi.onetouch.R.string as AppText

@Composable
fun Table(
    modifier : Modifier,
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
            text = stringResource(id = AppText.Name),
            value = state.name,
            onEvent = { onEvent(HOMEUIEvent.SetName(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            text = stringResource(id = AppText.Species),
            value = state.species,
            onEvent = { onEvent(HOMEUIEvent.SetSpecies(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            text = stringResource(id = AppText.Location),
            value = state.location,
            onEvent = { onEvent(HOMEUIEvent.SetLocation(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            text = stringResource(id = AppText.Date),
            value = state.date,
            onEvent = { onEvent(HOMEUIEvent.SetDate(it)) },
        )
        Divider(modifier = Modifier.fillMaxWidth())
        TableRow(
            modifier = Modifier.weight(1f),
            text = stringResource(id = AppText.Note),
            value = state.note,
            onEvent = { onEvent(HOMEUIEvent.SetNote(it)) },
        )
    }

}

@Preview(showBackground = true)
@Composable
fun RowPreview() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "text",
            modifier = Modifier
                .weight(0.2f)
                .height(40.dp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier)
        BasicTextField(
            modifier = Modifier
                .weight(0.7f)
                .height(40.dp)
                .wrapContentHeight(Alignment.CenterVertically),
            value = "value",
            onValueChange = { },
            textStyle = TextStyle.Default.copy(
                fontSize = 12.sp, color = Color.Black
            )
        )
        Icon(
            modifier = Modifier
                .weight(0.1f)
                .clickable { },
            painter = painterResource(id = R.drawable.ic_cancel),
            contentDescription = null
        )
    }
}