package com.dev_musashi.onetouch.uiLayer.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.dev_musashi.onetouch.uiLayer.composable.AddDialog
import com.dev_musashi.onetouch.uiLayer.composable.ButtonList
import com.dev_musashi.onetouch.uiLayer.composable.DeleteDialog
import com.dev_musashi.onetouch.uiLayer.home.composable.Table
import com.dev_musashi.onetouch.uiLayer.composable.addFocusCleaner
import com.dev_musashi.onetouch.uiLayer.util.snackBar.SnackBarManager
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
                        text = stringResource(id = AppText.OneTouch),
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

                //History List
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
                            if(ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED) {
                                onEvent(UIEvent.OpenCamera(openScreen))
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