package com.dev_musashi.onetouch.presentation.camera

data class CameraState(
    val name: String = "",
    val species: String = "",
    val location: String = "",
    val date: String = "",
    val note: String = "",
    val align0f: String = "BottomStart",
    val align90f: String = "TopStart",
    val align270f: String = "BottomEnd",
    val degree: Float = 0f,
    val doSaving: Boolean = false
)