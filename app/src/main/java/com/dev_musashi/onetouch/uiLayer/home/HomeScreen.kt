package com.dev_musashi.onetouch.uiLayer.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.onetouch.uiLayer.composable.ButtonList
import com.dev_musashi.onetouch.uiLayer.composable.CustomDialog
import com.dev_musashi.onetouch.uiLayer.home.composable.Table
import com.dev_musashi.onetouch.uiLayer.util.addFocusCleaner
import kotlinx.coroutines.launch
import com.dev_musashi.onetouch.R.drawable as AppImg
import com.dev_musashi.onetouch.R.string as AppText

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.state
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .addFocusCleaner(focusManager)
    ) {

        Box(
            modifier = Modifier
                .height(40.dp)
                .padding(10.dp, 10.dp, 0.dp, 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = AppText.원터치보드판),
                color = Color.Black,
                fontSize = 10.sp,
            )
        }


        Divider(color = Color.LightGray)


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = AppText.촬영히스토리),
                fontSize = 10.sp
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight(),
                    color = Color.Black
                )
                Icon(
                    modifier = Modifier.clickable { },
                    painter = painterResource(id = AppImg.ic_photo),
                    contentDescription = null
                )
            }
        }

        Divider(color = Color.LightGray)


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.15f)
                .background(Color.Black)
        ) {

        }

        Divider(color = Color.LightGray)


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = AppText.서식입력),
                fontSize = 10.sp
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Icon(
                    modifier = Modifier.clickable {
                        focusManager.clearFocus()
                        homeViewModel.tableSaveBtnClicked()
                    },
                    painter = painterResource(id = AppImg.ic_save),
                    contentDescription = null
                )
                Divider(
                    Modifier
                        .width(1.dp)
                        .fillMaxHeight(), color = Color.Black
                )
                Icon(
                    modifier = Modifier.clickable {
                        homeViewModel.addTableBtnClicked()
                    },
                    painter = painterResource(id = AppImg.ic_add),
                    contentDescription = null
                )
            }
        }

        Divider(color = Color.LightGray)
//        val list = homeViewModel.rooms.collectAsState(initial = emptyList())

        ButtonList(
            list = state.tableList,
            lazyListState = lazyListState,
            isSelectedState = state.isSelectedState,
            focusManager = focusManager,
            tableButtonClick = homeViewModel::tableButtonClicked,
            tableButtonLongClick = homeViewModel::onTableBtnLongClicked
        )

        Divider(color = Color.LightGray)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp),
        ) {
            if (state.tableList.isEmpty()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "---저장된 서식이 없습니다---"
                )
            } else {
                Table(
                    name = state.name,
                    nameChanged = homeViewModel::onNameChanged,
                    nameContent = state.nameContent,
                    nameContentChanged = homeViewModel::onNameContentChanged,
                    species = state.species,
                    speciesChanged = homeViewModel::onSpeciesChanged,
                    speciesContent = state.speciesContent,
                    speciesContentChanged = homeViewModel::onSpeciesContentChanged,
                    location = state.location,
                    locationChanged = homeViewModel::onLocationChanged,
                    locationContent = state.locationContent,
                    locationContentChanged = homeViewModel::onLocationContendChanged,
                    date = state.date,
                    dateChanged = homeViewModel::onDateChanged,
                    dateContent = state.dateContent,
                    dateContentChanged = homeViewModel::onDateContentChanged,
                    empty = state.empty,
                    emptyChanged = homeViewModel::onEmptyChanged,
                    emptyContent = state.emptyContent,
                    emptyContentChanged = homeViewModel::onEmptyContentChanged,
                    delete1BtnClicked = { /*TODO*/ },
                    delete2BtnClicked = { /*TODO*/ },
                    delete3BtnClicked = { /*TODO*/ },
                    delete4BtnClicked = { /*TODO*/ },
                    delete5BtnClicked = { /*TODO*/ },
                    tableScrollState = scrollState,
                    focusRequester = state.focusRequester
                )
            }
        }


        Divider(color = Color.LightGray)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.width(200.dp),
                onClick = { }
            ) {
                Text(text = "촬영", fontSize = 10.sp)
            }
        }

        Divider(color = Color.LightGray)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
        ) {

        }

        Divider(color = Color.LightGray)

    }

    if (state.openTitleDialog) {
        CustomDialog(
            modifier = Modifier
                .width(300.dp)
                .height(170.dp),
            title = AppText.tableTitle,
            content = {
                TextField(
                    modifier = Modifier
                        .padding(8.dp, 0.dp)
                        .focusRequester(state.focusRequester),
                    value = state.title,
                    onValueChange = homeViewModel::tableTitleTextChanged,
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
            },
            onDismissRequest = homeViewModel::addTableCancelClicked,
            onAcceptRequest = {
                homeViewModel.addTableOkayClicked()
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(0)
                }
            },
            acceptBtnEnabled = state.title.isNotBlank(),
        )
    }

    if (state.openDeleteTitleDialog) {
        CustomDialog(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp),
            title = AppText.서식삭제,
            content = {
                Text(text = stringResource(id = AppText.tableDelete))
            },
            onDismissRequest = homeViewModel::onTableBtnLongClickCanceled,
            onAcceptRequest = homeViewModel::onTableDeleted,
            acceptBtnEnabled = true
        )
    }
}



