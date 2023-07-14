package com.dev_musashi.onetouch.uiLayer.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.onetouch.R.string as AppText
import com.dev_musashi.onetouch.domain.model.TitleButton
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.usecase.UpsertTable
import com.dev_musashi.onetouch.domain.usecase.DeleteTable
import com.dev_musashi.onetouch.domain.usecase.GetTable
import com.dev_musashi.onetouch.domain.usecase.GetTableIdAndTitle
import com.dev_musashi.onetouch.domain.usecase.GetTables
import com.dev_musashi.onetouch.navigation.Screen
import com.dev_musashi.onetouch.uiLayer.util.snackBar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTables: GetTables,
    private val getTableIdAndTitle: GetTableIdAndTitle,
    private val getTable: GetTable,
    private val upsertTable: UpsertTable,
    private val deleteTable: DeleteTable
) : ViewModel() {

    private val _buttonList = MutableStateFlow<List<TitleButton>>(emptyList())
        .flatMapLatest { getTableIdAndTitle() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HomeState())

    val state = combine(_state, _buttonList) { state, buttonList ->
        state.copy(titleBtnList = buttonList)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    init {
        val restoredBtn = savedStateHandle.get<TitleButton>("button")
        if(restoredBtn != null) {
            println("restoreBtn : $restoredBtn")
            setButton(restoredBtn)
            setTable(restoredBtn)
        }
    }


    fun onEvent(event: UIEvent) {
        when (event) {

            UIEvent.AddTable -> {

                val table = Table(
                    title = state.value.title,
                    timestamp = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    upsertTable(table)
                }

                _state.update {
                    it.copy(isAddingTable = false, title = "")
                }

                SnackBarManager.showMessage("저장되었습니다.")

            }

            UIEvent.SaveTable -> {

                val currentTableBtn = state.value.currentBtn

                if (currentTableBtn != null) {

                    val id = currentTableBtn.id
                    val title = currentTableBtn.title
                    val name = state.value.name
                    val species = state.value.species
                    val location = state.value.location
                    val date = state.value.date
                    val note = state.value.note
                    val timestamp = currentTableBtn.timestamp

                    val table = Table(
                        id = id, title = title,
                        name = name, species = species,
                        location = location, date = date,
                        note = note, timestamp = timestamp
                    )

                    viewModelScope.launch {
                        upsertTable(table)
                    }

                    SnackBarManager.showMessage(AppText.SaveTitle)

                } else {
                    SnackBarManager.showMessage(AppText.NoSelectedTable)
                }
            }

            UIEvent.DeleteTable -> {
                if (state.value.currentBtn != null) {

                    val currentBtnId = state.value.currentBtn!!.id

                    savedStateHandle.remove<TitleButton>("button")

                    viewModelScope.launch {
                        deleteTable(currentBtnId)
                    }

                    _state.value.isSelected.remove(currentBtnId)

                    _state.update {
                        it.copy(
                            currentBtn = null,
                            isDeletingTable = false,
                        )
                    }

                    SnackBarManager.showMessage("삭제되었습니다.")

                }
            }

            UIEvent.ShowAddDialog -> {
                _state.update { it.copy(isAddingTable = true) }
            }

            UIEvent.HideAddDialog -> {
                _state.update { it.copy(isAddingTable = false, title = "") }
            }

            UIEvent.HideDelDialog -> {
                _state.update { it.copy(isDeletingTable = false) }
            }

            UIEvent.OpenGallery -> {
                println("갤러리 버튼 클릭")
            }

            is UIEvent.OpenCamera -> {
                val currentBtn = state.value.currentBtn
                if(currentBtn != null) {
                    event.open(Screen.CameraScreen.passId(currentBtn.id))
                } else {
                    SnackBarManager.showMessage(AppText.NoSelectedTable)
                }
            }

            is UIEvent.ShowDelDialog -> {

                savedStateHandle["button"] = event.btn

                setButton(event.btn)

                setTable(event.btn)

                _state.update { it.copy(currentBtn = event.btn, isDeletingTable = true) }

            }

            is UIEvent.ClickTitleBtn -> {

                println("테이블 클릭: ${event.btn}")

                savedStateHandle["button"] = event.btn

                setButton(event.btn)

                setTable(event.btn)

            }

            is UIEvent.SetDate -> {
                _state.update { it.copy(date = event.date) }
            }

            is UIEvent.SetLocation -> {
                _state.update { it.copy(location = event.location) }
            }

            is UIEvent.SetName -> {
                _state.update { it.copy(name = event.name) }
            }

            is UIEvent.SetNote -> {
                _state.update { it.copy(note = event.note) }
            }

            is UIEvent.SetSpecies -> {
                _state.update { it.copy(species = event.species) }
            }

            is UIEvent.SetTitle -> {
                _state.update { it.copy(title = event.title) }
            }


        }
    }

    private fun setButton(btn: TitleButton) {
        val selectedMap = _state.value.isSelected
        val selectedButton = selectedMap.entries.find { it.value }
        if (selectedButton != null) selectedMap[selectedButton.key] = false
        selectedMap[btn.id] = true
        _state.update { it.copy(currentBtn = btn) }
    }

    private fun setTable(btn: TitleButton) {
        viewModelScope.launch {
            val table = getTable(btn.id)
            println("get Table: $table")
            _state.update {
                it.copy(
                    name = table.name,
                    species = table.species,
                    location = table.location,
                    date = table.date,
                    note = table.note,
                )
            }
        }
    }
}




