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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.dev_musashi.onetouch.presentation.home.composable.rememberImeState
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
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val state by homeViewModel.state.collectAsState()
    val onEvent = homeViewModel::onEvent
    val lazyListState = rememberLazyListState()
    val scrollState = rememberScrollState()
    val imeState = rememberImeState()
    val intent = Intent(Intent.ACTION_VIEW)
        .apply { this.type = MediaStore.Images.Media.CONTENT_TYPE }

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.scrollTo(scrollState.maxValue)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .addFocusCleaner(focusManager)
            .padding(20.dp,10.dp,20.dp,20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        //One Touch Title & Gallery Button
        Row(
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.padding(5.dp),
            text = stringResource(id = AppText.History),
            style = MaterialTheme.typography.labelLarge
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(5.dp),
            colors = CardDefaults.cardColors(cardBGColor),
            elevation = CardDefaults.elevatedCardElevation(3.dp)
        ) {
            val dividerColor = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
            if (state.history.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    items(state.history) { history ->
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = history.str,
                            fontSize = 13.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Divider(color = dividerColor, thickness = 1.dp)
                    }
                }
            }

        }

        //보드판 서식 입력 Text & 저장 버튼  & 추가 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = AppText.BoardTitle),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold
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
                    contentDescription = "add",
                    tint = if(isSystemInDarkTheme()) Color.White else Color.Black
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
                .padding(5.dp),
            elevation = CardDefaults.elevatedCardElevation(3.dp),
            colors = CardDefaults.cardColors(tableCardBGColor)
        ) {
            if (state.currentBtn != null) {
                Table(
                    modifier = Modifier.weight(1f),
                    focusManager = focusManager,
                    state = state,
                    onEvent = onEvent
                )
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            Card(
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.BottomEnd),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(5.dp)
            ) {
                IconButton(
                    modifier = Modifier.fillMaxSize(),
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
                    colors = IconButtonDefaults.iconButtonColors(containerColor = if (isSystemInDarkTheme()) Purple40 else Purple80)
                ) {
                    Icon(
                        painter = painterResource(id = AppImg.ic_camera),
                        contentDescription = stringResource(id = AppText.Camera)
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