package com.dev_musashi.onetouch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dev_musashi.onetouch.navigation.Screen
import com.dev_musashi.onetouch.navigation.graph
import com.dev_musashi.onetouch.uiLayer.theme.OneTouchTheme

@Composable
fun MainContents() {
    OneTouchTheme {
        val appState = rememberAppState()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier,
                        snackbar = { snackBarData ->
                            Snackbar(snackBarData)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPadding ->
                NavHost(
                    navController = appState.navController,
                    startDestination = Screen.HomeScreen.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    graph(appState)
                }
            }
        }
    }
}