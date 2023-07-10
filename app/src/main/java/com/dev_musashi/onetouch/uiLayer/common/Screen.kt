package com.dev_musashi.onetouch.uiLayer.common

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "HOME")
    object CameraScreen : Screen(route = "CAMERA")
    object GalleryScreen : Screen(route = "GALLERY")
}