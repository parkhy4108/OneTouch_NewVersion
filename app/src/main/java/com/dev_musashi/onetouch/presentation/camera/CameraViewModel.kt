package com.dev_musashi.onetouch.presentation.camera

import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.onetouch.domain.usecase.GetTable
import com.dev_musashi.onetouch.domain.usecase.SaveImage
import com.dev_musashi.onetouch.domain.usecase.UpsertHistory
import com.dev_musashi.onetouch.sensor.RotationSensor
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarManager
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarMessage.Companion.toSnackBarMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.dev_musashi.onetouch.R.string as AppText

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val getTable: GetTable,
    private val upsertHistory: UpsertHistory,
    private val rotationSensor: RotationSensor,
    private val saveImage: SaveImage
) : ViewModel() {

    private val _state = MutableStateFlow(CameraState())
    private val _degree = rotationSensor.degree.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0f)

    val state = combine(_state, _degree) { state, degree ->
        state.copy(degree = degree)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CameraState())

    fun init(id: Int) {
        if (id != -1) {
            getTableContentById(id)
        }
        rotationSensor.startListening()
    }

    fun onEvent(event: CAMERAUIEvent) {
        when (event) {
            CAMERAUIEvent.BoardClick -> {

                if(state.value.degree == 0f) {
                    val newAlign = if (state.value.align0f == "BottomStart") "BottomEnd"
                    else "BottomStart"
                    _state.update { it.copy(align0f = newAlign) }
                }

                if(state.value.degree == 90f) {
                    val newAlign = if (state.value.align90f == "TopStart") "BottomEnd"
                    else "TopStart"
                    _state.update { it.copy(align90f = newAlign) }
                }

                if(state.value.degree == 270f) {
                    val newAlign = if (state.value.align270f == "TopEnd") "BottomEnd" else "TopEnd"
                    _state.update { it.copy(align270f = newAlign) }
                }

            }

            CAMERAUIEvent.FlashClick -> {
                _state.update { it.copy(flash = !state.value.flash) }
            }


            is CAMERAUIEvent.TakePictureButton -> {
                viewModelScope.launch(Dispatchers.IO) {
                    saveHistory()
                    saveImage(event.captureImg, event.pictureImg)
                }
            }
        }
    }

    private fun getTableContentById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val table = getTable(id)
            _state.update {
                it.copy(
                    name = table.name,
                    species = table.species,
                    location = table.location,
                    date = table.date,
                    note = table.note
                )
            }
        }
    }

    private fun saveHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            upsertHistory(
                name = state.value.name,
                species = state.value.species,
                location = state.value.location,
                date = state.value.date,
                note = state.value.note
            )
        }
    }

    private fun saveImage(captureImg: ImageBitmap, pictureImg: ImageProxy) {
        viewModelScope.launch(Dispatchers.IO) {
            saveImage(captureImg, pictureImg) { exception ->
                if (exception != null) SnackBarManager.showMessage(exception.toSnackBarMessage())
                else SnackBarManager.showMessage(AppText.SuccessSaveImg)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        rotationSensor.stopListening()
    }

}