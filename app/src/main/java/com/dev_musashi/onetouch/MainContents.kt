package com.dev_musashi.onetouch

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dev_musashi.onetouch.presentation.common.navigation.Screen
import com.dev_musashi.onetouch.presentation.common.navigation.graph
import com.dev_musashi.onetouch.presentation.theme.OneTouchTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainContents() {
    OneTouchTheme {
        val appState = rememberAppState()
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackBarHostState,
                        modifier = Modifier,
                        snackbar = { snackBarData ->
                            Snackbar(snackBarData)
                        }
                    )
                }
            ) { _ ->
                NavHost(
                    navController = appState.navController,
                    startDestination = Screen.HomeScreen.route
                ) {
                    graph(appState)
                }
            }
        }
    }
}