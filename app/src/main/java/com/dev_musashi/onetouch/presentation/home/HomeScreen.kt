package com.dev_musashi.onetouch.presentation.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.dev_musashi.onetouch.presentation.theme.Purple40
import com.dev_musashi.onetouch.presentation.theme.Purple80
import com.dev_musashi.onetouch.presentation.theme.Yellow40
import com.dev_musashi.onetouch.presentation.theme.Yellow80
import com.dev_musashi.onetouch.R.drawable as AppImg
import com.dev_musashi.onetouch.R.string as AppText

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    openScreen: (String) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state by homeViewModel.state.collectAsState()
    val onEvent = homeViewModel::onEvent
    val focusManager = LocalFocusManager.current
    val lazyListState = rememberLazyListState()
    val context = LocalContext.current
    val intent =
        Intent(Intent.ACTION_VIEW).apply { this.type = MediaStore.Images.Media.CONTENT_TYPE }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
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
                },
                containerColor = if (isSystemInDarkTheme()) Purple40 else Purple80,
                shape = CircleShape
            ) {
                Icon(
                    painter = painterResource(id = AppImg.ic_camera),
                    contentDescription = stringResource(id = AppText.Camera)
                )
            }
        }
    ) { _ ->
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize()
                .addFocusCleaner(focusManager)
        ) {
            //One Touch Title & Gallery Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = AppText.OneTouch),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    modifier = Modifier.clickable {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            context.startActivity(intent)
                        } else {
                            SnackBarManager.showMessage(AppText.StoragePermissionDenied)
                        }
                    },
                    painter = painterResource(id = AppImg.ic_photo),
                    contentDescription = "gallery"
                )
            }

            //History List
            val cardBGColor = if (isSystemInDarkTheme()) Purple40 else Purple80
            Text(
                modifier = Modifier
                    .padding(20.dp, 0.dp),
                text = stringResource(id = AppText.History),
                style = MaterialTheme.typography.labelLarge
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(20.dp),
                colors = CardDefaults.cardColors(cardBGColor),
                elevation = CardDefaults.elevatedCardElevation(3.dp)
            ) {
                val dividerColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
                if (state.history.isNotEmpty()) {
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                        items(state.history) { history ->
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = history.str,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Divider(color = dividerColor,thickness = 1.dp)
                        }
                    }
                }

            }

            //보드판 서식 입력 Text & 저장 버튼  & 추가 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = AppText.BoardTitle),
                    style = MaterialTheme.typography.headlineMedium,
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

            //버튼 리스트
            AnimatedVisibility(
                visible = state.titleBtnList.isNotEmpty(),
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                ButtonList(
                    list = state.titleBtnList,
                    lazyListState = lazyListState,
                    isSelectedState = state.isSelected,
                    focusManager = focusManager,
                    onEvent = onEvent
                )
            }

            //테이블 content 영역
            val tableCardBGColor = if (isSystemInDarkTheme()) Yellow40 else Yellow80
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(20.dp),
                elevation = CardDefaults.elevatedCardElevation(3.dp),
                colors = CardDefaults.cardColors(tableCardBGColor)
            ) {
                if(state.currentBtn != null) {
                    Table(
                        modifier = Modifier.weight(1f),
                        state = state,
                        onEvent = onEvent
                    )
                }
            }
        }
    }

    if (state.isAddingTable) {
        AddDialog(state = state, onEvent = onEvent)
    }

    if (state.isDeletingTable) {
        DeleteDialog(onEvent = onEvent)
    }

}