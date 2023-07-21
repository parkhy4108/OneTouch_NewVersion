package com.dev_musashi.onetouch.presentation.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dev_musashi.onetouch.AppState
import com.dev_musashi.onetouch.presentation.camera.CameraScreen
import com.dev_musashi.onetouch.presentation.home.HomeScreen

fun NavGraphBuilder.graph(appState: AppState) {
    composable(route = Screen.HomeScreen.route) {
        HomeScreen(openScreen = { route -> appState.navigate(route) })
    }
    composable(
        route = Screen.CameraScreen.route,
        arguments = listOf(navArgument(CAMERA_ButtonId) {
            type = NavType.IntType
        })
    ) {
        val id = it.arguments?.getInt(CAMERA_ButtonId) ?: -1
        CameraScreen(buttonId = id)
    }

    composable(route = Screen.GalleryScreen.route) {

    }
}