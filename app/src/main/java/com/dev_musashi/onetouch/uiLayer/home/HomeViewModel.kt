package com.dev_musashi.onetouch.uiLayer.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.usecase.UpsertTable
import com.dev_musashi.onetouch.domain.usecase.DeleteTable
import com.dev_musashi.onetouch.domain.usecase.GetTable
import com.dev_musashi.onetouch.domain.usecase.GetTables
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
    private val getTables: GetTables,
    private val getTable: GetTable,
    private val upsertTable: UpsertTable,
    private val deleteTable: DeleteTable
) : ViewModel() {

    private val _list = MutableStateFlow<List<Table>>(emptyList())
        .flatMapLatest { getTables() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HomeState())
    private var currentTable: Table? = null

    val state = combine(_state, _list) { state, list ->
        if(list.isNotEmpty()) state.isSelectedState[list[0].id] = true
        state.copy(tables = list)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeState())

    fun onEvent(event: UIEvent) {
        when (event) {

            UIEvent.AddTable -> {
                println("테이블 추가")
                val table = Table(
                    title = state.value.title,
                    name = "",
                    species = "",
                    location = "",
                    date = "",
                    note = "",
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
                if(currentTable != null) {

                    val id = currentTable!!.id
                    val title = currentTable!!.title
                    val name = state.value.name
                    val species = state.value.species
                    val location = state.value.location
                    val date = state.value.date
                    val note = state.value.note
                    val timestamp = currentTable!!.timestamp

                    val table = Table(
                        id = id,
                        title = title,
                        name = name,
                        species = species,
                        location = location,
                        date = date,
                        note = note,
                        timestamp = timestamp
                    )
                    viewModelScope.launch {
                        upsertTable(table)
                    }

                    SnackBarManager.showMessage("저장되었습니다.")

                } else {
                    println("선택된 테이블 없음 저장 안됨")
                }
            }

            UIEvent.DeleteTable -> {
                viewModelScope.launch {
                    deleteTable(currentTable!!)
                }
                _state.value.isSelectedState.remove(currentTable!!.id)
                _state.update { it.copy(isDeletingTable = false, isSelectedState = _state.value.isSelectedState) }
                SnackBarManager.showMessage("삭제되었습니다.")
            }

            UIEvent.ShowAddDialog -> {
                println("테이블 추가 다이얼로그 오픈")
                _state.update { it.copy(isAddingTable = true) }
            }

            UIEvent.ShowDelDialog -> {
                println("테이블 삭제 다이얼로그 오픈")
                _state.update { it.copy(isDeletingTable = true) }
            }

            UIEvent.HideAddDialog -> {
                _state.update {
                    println("테이블 추가 다이얼로그 취소")
                    it.copy(isAddingTable = false, title = "")
                }
            }

            UIEvent.HideDelDialog -> {
                _state.update {
                    println("테이블 삭제 다이얼로그 취소")
                    it.copy(isDeletingTable = false)
                }
            }

            UIEvent.OpenGallery -> {
                println("갤러리 버튼 클릭")
            }

            UIEvent.OpenCamera -> {
                println("촬영 버튼 클릭")
            }

            is UIEvent.ClickTable -> {
                println("테이블 클릭: ${event.table}")
                viewModelScope.launch {
                    val table = getTable(event.table.id)
                    currentTable = event.table
                    val currentSelectedId = _state.value.isSelectedState.entries.find { it.value }!!.key
                    if(currentSelectedId != event.table.id) {
                        _state.value.isSelectedState[currentSelectedId] = false
                        _state.value.isSelectedState[event.table.id] = true
                    }
                    _state.update {
                        it.copy(
                            name = table.name,
                            species = table.species,
                            location = table.location,
                            date = table.date,
                            note = table.note,
                            isSelectedState = _state.value.isSelectedState
                        )
                    }
                }
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


}

