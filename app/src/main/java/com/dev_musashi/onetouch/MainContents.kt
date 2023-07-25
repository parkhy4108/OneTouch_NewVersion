package com.dev_musashi.onetouch

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.dev_musashi.onetouch.presentation.common.navigation.Screen
import com.dev_musashi.onetouch.presentation.common.navigation.graph
import com.dev_musashi.onetouch.presentation.theme.OneTouchTheme

@Composable
fun MainContents() {
    OneTouchTheme {
        val appState = rememberAppState()
        Surface(
            modifier = Modifier.fillMaxSize(),
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