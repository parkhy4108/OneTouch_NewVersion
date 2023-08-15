package com.dev_musashi.onetouch.presentation.camera

import androidx.camera.core.ImageProxy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.onetouch.domain.usecase.GetTable
import com.dev_musashi.onetouch.domain.usecase.SaveImage
import com.dev_musashi.onetouch.domain.usecase.UpsertHistory
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarManager
import com.dev_musashi.onetouch.presentation.common.snackBar.SnackBarMessage.Companion.toSnackBarMessage
import com.dev_musashi.onetouch.sensor.RotationSensor
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

            is CAMERAUIEvent.TakePictureButton -> {
                viewModelScope.launch {
                    _state.update { it.copy(doSaving = true) }
                    saveHistory()
                    saveImg(event.captureImg, event.pictureImg)
                    _state.update { it.copy(doSaving = false) }
                }
            }
        }
    }

    private fun getTableContentById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val table = getTable(id)
            _state.update {
                it.copy(
                    name = table.name, nameContent = table.nameContent,
                    species = table.species, speciesContent = table.speciesContent,
                    location = table.location, locationContent = table.locationContent,
                    date = table.date, dateContent = table.dateContent,
                    note = table.note, noteContent = table.noteContent
                )
            }
        }
    }

    private fun saveHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            upsertHistory(
                name = state.value.nameContent,
                species = state.value.speciesContent,
                location = state.value.locationContent,
                date = state.value.dateContent,
                note = state.value.noteContent
            )
        }
    }

    private suspend fun saveImg(captureImg: ImageBitmap, pictureImg: ImageProxy) {
        val saveResult = saveImage(captureImg, pictureImg)
        if(saveResult != null) SnackBarManager.showMessage(saveResult.toSnackBarMessage())
        else SnackBarManager.showMessage(AppText.SuccessSaveImg)
    }

    override fun onCleared() {
        super.onCleared()
        rotationSensor.stopListening()
    }

}