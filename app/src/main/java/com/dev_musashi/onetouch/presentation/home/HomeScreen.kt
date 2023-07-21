package com.dev_musashi.onetouch.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.dev_musashi.onetouch.presentation.common.composable.addFocusCleaner
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarManager
import com.dev_musashi.onetouch.presentation.home.composable.AddDialog
import com.dev_musashi.onetouch.presentation.home.composable.ButtonList
import com.dev_musashi.onetouch.presentation.home.composable.DeleteDialog
import com.dev_musashi.onetouch.presentation.home.composable.Table
import com.dev_musashi.onetouch.R.drawable as AppImg
import com.dev_musashi.onetouch.R.string as AppText

@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val onEvent = homeViewModel::onEvent
    val focusManager = LocalFocusManager.current
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
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
                        text = stringResource(id = AppText.OneTouch),
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(
                        modifier = Modifier.clickable { onEvent(HOMEUIEvent.OpenGallery) },
                        painter = painterResource(id = AppImg.ic_photo),
                        contentDescription = "gallery"
                    )
                }

                //History List
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.Black)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(state.history) { history ->
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = history.str,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                            Divider(color = Color.LightGray, thickness = 1.dp)
                        }
                    }
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
                        text = stringResource(id = AppText.BoardTitle),
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
                                onEvent(HOMEUIEvent.SaveTable)
                            },
                            painter = painterResource(id = AppImg.ic_save),
                            contentDescription = null
                        )
                        Icon(
                            modifier = Modifier.clickable {
                                onEvent(HOMEUIEvent.ShowAddDialog)
                            },
                            painter = painterResource(id = AppImg.ic_add),
                            contentDescription = "add"
                        )
                    }
                }
                Divider(color = Color.LightGray)
                Spacer(modifier = Modifier.height(3.dp))
                //버튼 리스트
                ButtonList(
                    list = state.titleBtnList,
                    lazyListState = lazyListState,
                    isSelectedState = state.isSelected,
                    focusManager = focusManager,
                    onEvent = onEvent
                )
                Spacer(modifier = Modifier.height(3.dp))

                Divider(color = Color.LightGray)

                //테이블 content 영역
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    if (state.currentBtn != null) {
                        Table(
                            state = state,
                            onEvent = onEvent,
                            focusRequester = focusRequester
                        )
                    }
                }

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
                        onClick = {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                onEvent(HOMEUIEvent.OpenCamera(openScreen))
                            } else {
                                SnackBarManager.showMessage(AppText.CameraPermissionDenied)
                            }

                        }
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
        AddDialog(state = state, onEvent = onEvent)
    }

    if (state.isDeletingTable) {
        DeleteDialog(onEvent = onEvent)
    }
}