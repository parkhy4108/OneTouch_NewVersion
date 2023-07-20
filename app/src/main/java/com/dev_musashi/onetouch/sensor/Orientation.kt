package com.dev_musashi.onetouch.sensor

import android.content.Context
import android.view.OrientationEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class Orientation(
    private val context: Context
) {

    private lateinit var listener : OrientationEventListener
    private val _state = MutableStateFlow(-1)
    val state: StateFlow<Int> = _state

    fun startListening(){
        listener = object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                if(orientation == ORIENTATION_UNKNOWN) {
                    _state.value = -1
                }
                when(orientation) {
                    in 45 until 135 -> _state.value = 90
                    in 135 until 225 -> _state.value = 180
                    in 225 until 315 -> _state.value = 270
                    else -> _state.value = 360
                }
            }
        }
        listener.enable()
    }

    fun stopListening(){
        listener.disable()
        println("listener disable")
    }



}