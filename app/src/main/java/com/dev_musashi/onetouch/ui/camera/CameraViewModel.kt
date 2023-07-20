package com.dev_musashi.onetouch.ui.camera

import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.onetouch.data.gallery.Gallery
import com.dev_musashi.onetouch.domain.usecase.GetTable
import com.dev_musashi.onetouch.domain.usecase.UpsertHistory
import com.dev_musashi.onetouch.sensor.RotationSensor
import com.dev_musashi.onetouch.ui.util.snackBar.SnackBarManager
import com.dev_musashi.onetouch.R.string as AppText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val getTable: GetTable,
    private val upsertHistory: UpsertHistory,
    private val rotationSensor: RotationSensor,
    private val gallery: Gallery
) : ViewModel() {

    var state = mutableStateOf(CameraState())
        private set

    val rotationState = rotationSensor.state

    private val cal: Calendar = Calendar.getInstance()
    private val hour: Int = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time).toInt()
    private val min: Int = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time).toInt()

    fun init(id: Int) {
        if (id != -1) {
            getTableContentById(id)
        }
        rotationSensor.startListening()
    }


    fun onEvent(event: CAMERAUIEvent) {
        when (event) {
            CAMERAUIEvent.BoardClick -> {
                val currentAlign = state.value.align
                state.value = if (currentAlign == "TopStart") {
                    state.value.copy(align = "BottomStart")
                } else {
                    state.value.copy(align = "TopStart")
                }

            }


            // 1. history save
            is CAMERAUIEvent.TakePictureButton -> {

                viewModelScope.launch {
                    saveHistory()
                    saveImage(event.captureImg, event.pictureImg)
                }

            }
        }
    }


    private fun getTableContentById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val table = getTable(id)
            state.value = state.value.copy(
                name = table.name,
                species = table.species,
                location = table.location,
                date = table.date,
                note = table.note
            )
        }
    }

    private suspend fun saveHistory() = viewModelScope.launch {
        upsertHistory(
            name = state.value.name,
            species = state.value.species,
            location = state.value.location,
            date = state.value.date,
            note = state.value.note
        )
    }

    private suspend fun saveImage(captureImg: ImageBitmap, pictureImg: Bitmap){
        viewModelScope.launch {
            gallery.saveImage(captureImg, pictureImg) { exception ->
                if(exception != null) SnackBarManager.showMessage(exception)
                else SnackBarManager.showMessage(AppText.SuccessSaveImg)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        rotationSensor.stopListening()
    }



}