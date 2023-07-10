package com.dev_musashi.onetouch.uiLayer.common

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dev_musashi.onetouch.uiLayer.home.HomeScreen

fun NavGraphBuilder.graph() {
    composable(route = Screen.HomeScreen.route) {
        HomeScreen()
    }
    composable(route = Screen.CameraScreen.route) {

    }
    composable(route = Screen.GalleryScreen.route) {

    }
}