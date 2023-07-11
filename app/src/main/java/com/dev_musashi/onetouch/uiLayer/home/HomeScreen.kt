package com.dev_musashi.onetouch.uiLayer.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.onetouch.R
import com.dev_musashi.onetouch.uiLayer.composable.ButtonList
import com.dev_musashi.onetouch.uiLayer.composable.CustomDialog
import com.dev_musashi.onetouch.uiLayer.home.composable.Table
import com.dev_musashi.onetouch.uiLayer.home.composable.TableRow
import com.dev_musashi.onetouch.uiLayer.util.addFocusCleaner
import kotlinx.coroutines.launch
import com.dev_musashi.onetouch.R.drawable as AppImg
import com.dev_musashi.onetouch.R.string as AppText

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val onEvent = homeViewModel::onEvent
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .addFocusCleaner(focusManager)
            ) {

                //One Touch Title & Gallery Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = AppText.원터치보드판),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        modifier = Modifier.clickable { onEvent(UIEvent.OpenGallery) },
                        painter = painterResource(id = AppImg.ic_photo),
                        contentDescription = "gallery"
                    )
                }

                //히스토리 리스트뷰
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Black)
                ) {

                }

                //보드판 서식 입력 Text & 저장 버튼  & 추가 버튼
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
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(
                            modifier = Modifier.clickable {
                                focusManager.clearFocus()
                                onEvent(UIEvent.SaveTable)
                            },
                            painter = painterResource(id = AppImg.ic_save),
                            contentDescription = null
                        )
                        Icon(
                            modifier = Modifier.clickable {
                                onEvent(UIEvent.ShowAddDialog)
                            },
                            painter = painterResource(id = AppImg.ic_add),
                            contentDescription = "add"
                        )
                    }
                }
                Divider(color = Color.LightGray)

                //버튼 리스트
                ButtonList(
                    list = state.tables,
                    lazyListState = lazyListState,
                    isSelectedState = state.isSelectedState,
                    focusManager = focusManager,
                    onEvent = onEvent
                )
                Divider(color = Color.LightGray)

                //테이블 content 영역
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    if (state.tables.isEmpty()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = stringResource(id = AppText.서식없음)
                        )
                    } else {
                        Table(
                            state = state,
                            onEvent = onEvent,
                            scrollState = scrollState,
                            focusRequester = focusRequester
                        )
                    }
                }
//                Divider(color = Color.LightGray)

                //촬영 버튼 영역
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
                        onClick = { onEvent(UIEvent.OpenCamera) }
                    ) {
                        Text(text = "촬영", fontSize = 10.sp)
                    }
                }
                Divider(color = Color.LightGray)
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(600.dp)
            ) {

            }
            Divider(color = Color.LightGray)
        }
    }

    if (state.isAddingTable) {
        CustomDialog(
            modifier = Modifier
                .width(300.dp)
                .height(200.dp),
            title = stringResource(id = AppText.tableTitle),
            content = {
                TextField(
                    modifier = Modifier
                        .padding(8.dp, 0.dp)
                        .focusRequester(state.focusRequester),
                    value = state.title,
                    onValueChange = { onEvent(UIEvent.SetTitle(it)) },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
            },
            cancel = { onEvent(UIEvent.HideAddDialog)},
            okay = {
                onEvent(UIEvent.AddTable)
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(0)
                }
            },
            acceptBtnEnabled = state.title.isNotBlank(),
        )
    }

    if (state.isDeletingTable) {
        CustomDialog(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp),
            title = stringResource(id = AppText.서식삭제),
            content = {
                Text(text = stringResource(id = AppText.tableDelete))
            },
            cancel = { onEvent(UIEvent.HideDelDialog) },
            okay = { onEvent(UIEvent.DeleteTable) },
            acceptBtnEnabled = true
        )
    }
}

//Column(
//modifier = Modifier
//.fillMaxSize()
//.addFocusCleaner(focusManager)
//) {
//
//    Box(
//        modifier = Modifier
//            .height(40.dp)
//            .padding(10.dp, 10.dp, 0.dp, 10.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = stringResource(id = AppText.원터치보드판),
//            color = Color.Black,
//            fontSize = 10.sp,
//        )
//    }
//    Divider(color = Color.LightGray)
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(40.dp)
//            .padding(10.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(text = stringResource(id = AppText.촬영히스토리), fontSize = 10.sp)
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(5.dp)
//        ) {
//            Divider(
//                Modifier
//                    .width(1.dp)
//                    .fillMaxHeight(), color = Color.Black
//            )
//            Icon(
//                modifier = Modifier.clickable { onEvent(UIEvent.OpenGallery) },
//                painter = painterResource(id = AppImg.ic_photo),
//                contentDescription = "gallery"
//            )
//        }
//    }
//    Divider(color = Color.LightGray)
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(0.15f)
//            .background(Color.Black)
//    ) {
//
//    }
//    Divider(color = Color.LightGray)
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(40.dp)
//            .padding(10.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(text = stringResource(id = AppText.서식입력), fontSize = 10.sp)
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(5.dp)
//        ) {
//            Icon(
//                modifier = Modifier.clickable {
//                    focusManager.clearFocus()
//                    onEvent(UIEvent.SaveTable)
//                },
//                painter = painterResource(id = AppImg.ic_save),
//                contentDescription = null
//            )
//            Divider(
//                Modifier
//                    .width(1.dp)
//                    .fillMaxHeight(), color = Color.Black
//            )
//            Icon(
//                modifier = Modifier.clickable {
//                    onEvent(UIEvent.ShowAddDialog)
//                },
//                painter = painterResource(id = AppImg.ic_add),
//                contentDescription = "add"
//            )
//        }
//    }
//
//    Divider(color = Color.LightGray)
//
//    ButtonList(
//        list = state.tables,
//        lazyListState = lazyListState,
//        isSelectedState = state.isSelectedState,
//        focusManager = focusManager,
//        onEvent = onEvent
//    )
//    Divider(color = Color.LightGray)
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(170.dp)
//        ,
//    ) {
//        if (state.tables.isEmpty()) {
//            Text(
//                modifier = Modifier.align(Alignment.Center),
//                text = stringResource(id = AppText.서식없음)
//            )
//        } else {
//            Table(
//                state = state,
//                onEvent = onEvent,
//                scrollState = scrollState,
//                focusRequester = focusRequester
//            )
//        }
//    }
//    Divider(color = Color.LightGray)
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(50.dp)
//            .padding(10.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center
//    ) {
//        Button(
//            modifier = Modifier.width(200.dp),
//            onClick = { onEvent(UIEvent.OpenCamera) }
//        ) {
//            Text(text = "촬영", fontSize = 10.sp)
//        }
//    }
//    Divider(color = Color.LightGray)
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxSize()
//    ) {
//
//    }
//    Divider(color = Color.LightGray)
//}


@Preview(showBackground = true)
@Composable
fun HomePreview(){
    val focusManager = LocalFocusManager.current
    LazyColumn {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .addFocusCleaner(focusManager)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = AppText.원터치보드판), fontSize = 10.sp,)
                    Icon(
                        modifier = Modifier.clickable {  },
                        painter = painterResource(id = AppImg.ic_photo),
                        contentDescription = "gallery"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Black)
                ) {

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(id = AppText.서식입력), fontSize = 10.sp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Icon(
                            modifier = Modifier.clickable {},
                            painter = painterResource(id = AppImg.ic_save),
                            contentDescription = null
                        )
                        Icon(
                            modifier = Modifier.clickable {},
                            painter = painterResource(id = AppImg.ic_add),
                            contentDescription = "add"
                        )
                    }
                }

                val lazyListState = rememberLazyListState()
                ButtonList(
                    list = emptyList(),
                    lazyListState = lazyListState,
                    isSelectedState = mutableMapOf(),
                    focusManager = focusManager,
                    onEvent = {}
                )

                val focusRequester = FocusRequester()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        TableRow(
                            text = stringResource(id = AppText.공사명),
                            value = "state.name",
                            onEvent = { },
                            focusRequester = focusRequester
                        )
                        Divider(modifier = Modifier.fillMaxWidth())
                        TableRow(
                            text = stringResource(id = AppText.공종),
                            value = "state.species",
                            onEvent = { },
                            focusRequester = focusRequester
                        )
                        Divider(modifier = Modifier.fillMaxWidth())
                        TableRow(
                            text = stringResource(id = AppText.위치),
                            value = "state.location",
                            onEvent = { },
                            focusRequester = focusRequester
                        )
                        Divider(modifier = Modifier.fillMaxWidth())
                        TableRow(
                            text = stringResource(id = AppText.날짜),
                            value = "state.date",
                            onEvent = { },
                            focusRequester = focusRequester
                        )
                        Divider(modifier = Modifier.fillMaxWidth())
                        TableRow(
                            text = stringResource(id = AppText.비고),
                            value = "state.note",
                            onEvent = { },
                            focusRequester = focusRequester
                        )
                    }
                }

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
                        onClick = {  }
                    ) {
                        Text(text = "촬영", fontSize = 10.sp)
                    }
                }
                Divider(color = Color.LightGray)
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {

            }
            Divider(color = Color.LightGray)
        }
    }
}
