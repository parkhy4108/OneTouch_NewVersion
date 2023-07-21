package com.dev_musashi.onetouch.presentation.common.navigation

const val CAMERA_ButtonId = "id"

sealed class Screen(val route: String) {
    object HomeScreen : Screen(route = "HOME")
    object CameraScreen : Screen(route = "CAMERA/{$CAMERA_ButtonId}") {
        fun passId(id: Int): String = this.route.replace(oldValue = "{$CAMERA_ButtonId}", newValue = id.toString())
    }
    object GalleryScreen : Screen(route = "GALLERY")

}