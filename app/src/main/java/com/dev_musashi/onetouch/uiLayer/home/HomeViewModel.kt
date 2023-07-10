package com.dev_musashi.onetouch.uiLayer.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.usecase.InsertTable
import com.dev_musashi.onetouch.domain.usecase.DeleteTable
import com.dev_musashi.onetouch.domain.usecase.GetTable
import com.dev_musashi.onetouch.domain.usecase.GetTables
import com.dev_musashi.onetouch.uiLayer.util.snackBar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.dev_musashi.onetouch.R.string as AppText

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTables: GetTables,
    private val getTable: GetTable,
    private val insertTable: InsertTable,
    private val deleteTable: DeleteTable
) : ViewModel() {

    var state = mutableStateOf(HomeState())
        private set

    private val _list = MutableStateFlow<List<Table>>(emptyList())

    val list: StateFlow<List<Table>> = _list.asStateFlow()

//    var getTablesJob: Job? = null
    private var currentTable: Table? = null
    private var currentIndex: Int? = null
    private var deletingIndex: Int? = null
    private var deletingTable: Table? = null
    private val tableList get() = state.value.tableList
    private val isSelectedState get() = state.value.isSelectedState
    private val title get() = state.value.title
    private val name get() = state.value.name
    private val nameContent get() = state.value.nameContent
    private val species get() = state.value.species
    private val speciesContent get() = state.value.speciesContent
    private val location get() = state.value.location
    private val locationContent get() = state.value.locationContent
    private val date get() = state.value.date
    private val dateContent get() = state.value.dateContent
    private val empty get() = state.value.empty
    private val emptyContent get() = state.value.emptyContent

    init {
        getAllTables()
    }

    private fun getAllTables() {
        viewModelScope.launch {
            getTables().collect { tables ->
                if (tables.isNotEmpty()) {
                    val list = tables.sortedByDescending { table -> table.timestamp }
                    state.value = state.value.copy(tableList = list)
                    if (currentIndex != null) isSelectedState[currentIndex!!] = false
                    currentTable = list[0]
                    currentIndex = 0
                    isSelectedState[0] = true
                    state.value = state.value.copy(isSelectedState = isSelectedState)
                    onTableStateChanged(table = list[0])
                } else state.value = state.value.copy(tableList = emptyList())
            }
        }
    }



    fun tableTitleTextChanged(newValue: String) {
        state.value = state.value.copy(title = newValue)
    }

    fun onNameChanged(newValue: String) {
        state.value = state.value.copy(name = newValue)
    }

    fun onNameContentChanged(newValue: String) {
        state.value = state.value.copy(nameContent = newValue)
    }

    fun onSpeciesChanged(newValue: String) {
        state.value = state.value.copy(species = newValue)
    }

    fun onSpeciesContentChanged(newValue: String) {
        state.value = state.value.copy(speciesContent = newValue)
    }

    fun onLocationChanged(newValue: String) {
        state.value = state.value.copy(location = newValue)
    }

    fun onLocationContendChanged(newValue: String) {
        state.value = state.value.copy(locationContent = newValue)
    }

    fun onDateChanged(newValue: String) {
        state.value = state.value.copy(date = newValue)
    }

    fun onDateContentChanged(newValue: String) {
        state.value = state.value.copy(dateContent = newValue)
    }

    fun onEmptyChanged(newValue: String) {
        state.value = state.value.copy(empty = newValue)
    }

    fun onEmptyContentChanged(newValue: String) {
        state.value = state.value.copy(emptyContent = newValue)
    }

    private fun onTableStateChanged(table: Table) {
        state.value = state.value.copy(
            name = table.name,
            nameContent = table.nameContent,
            species = table.species,
            speciesContent = table.speciesContent,
            location = table.location,
            locationContent = table.locationContent,
            date = table.date,
            dateContent = table.dateContent,
            empty = table.empty,
            emptyContent = table.emptyContent
        )
    }

    fun tableButtonClicked(table: Table, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val newTable = getTable(table.id)
            println("load table: $newTable")
            if(currentIndex != null) isSelectedState[currentIndex!!] = false
            currentTable = newTable
            currentIndex = index
            isSelectedState[index] = true
            state.value = state.value.copy(isSelectedState = isSelectedState)
            onTableStateChanged(newTable)
        }
    }

    fun tableSaveBtnClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            insertTable(
                table = Table(
                    id = currentTable!!.id,
                    title = tableList[currentIndex!!].title,
                    name = name,
                    nameContent = nameContent,
                    species = species,
                    speciesContent = speciesContent,
                    location = location,
                    locationContent = locationContent,
                    date = date,
                    dateContent = dateContent,
                    empty = empty,
                    emptyContent = emptyContent,
                    timestamp = currentTable!!.timestamp
                )
            )
            SnackBarManager.showMessage(AppText.save)
        }
    }

    fun addTableBtnClicked() {
        state.value = state.value.copy(openTitleDialog = true)
    }

    fun addTableOkayClicked() {
        viewModelScope.launch(Dispatchers.IO) {
            insertTable(
                table = Table(
                    id = System.currentTimeMillis(),
                    title = title,
                    name = "공사명",
                    nameContent = "",
                    species = "공 종",
                    speciesContent = "",
                    location = "위 치",
                    locationContent = "",
                    date = "날 짜",
                    dateContent = "",
                    empty = "비 고",
                    emptyContent = "",
                    timestamp = System.currentTimeMillis()
                )
            )
            state.value = state.value.copy(openTitleDialog = false, title = "")
        }
    }

    fun addTableCancelClicked() {
        state.value = state.value.copy(openTitleDialog = false, title = "")
    }

    fun onTableBtnLongClicked(table: Table, index: Int) {
        deletingTable = table
        deletingIndex = index
        state.value = state.value.copy(openDeleteTitleDialog = true)
    }

    fun onTableBtnLongClickCanceled() {
        deletingTable = null
        deletingIndex = null
        state.value = state.value.copy(openDeleteTitleDialog = false)
    }

    fun onTableDeleted() {
        isSelectedState.remove(deletingIndex)
        state.value = state.value.copy(isSelectedState = isSelectedState)
        viewModelScope.launch(Dispatchers.IO) {
            deletingTable?.let { deleteTable(table = it) }
            onTableBtnLongClickCanceled()
        }
    }


}

