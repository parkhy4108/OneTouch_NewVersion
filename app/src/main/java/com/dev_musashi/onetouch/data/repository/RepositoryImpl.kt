package com.dev_musashi.onetouch.data.repository

import com.dev_musashi.onetouch.data.data_source.Dao
import com.dev_musashi.onetouch.domain.model.History
import com.dev_musashi.onetouch.domain.model.TitleButton
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: Dao
) : Repository {

    private val cal: Calendar = Calendar.getInstance()
    private val hour: Int = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time).toInt()
    private val min: Int = SimpleDateFormat("mm", Locale.getDefault()).format(cal.time).toInt()

    override fun getAllTables(): Flow<List<Table>> {
        return dao.getTables()
    }

    override fun getTableIdAndTitle(): Flow<List<TitleButton>> {
        return dao.getTablesIdAndTitle()
    }

    override suspend fun getTable(id: Int): Table {
        return dao.getTable(id)
    }

    override suspend fun upsertTable(table: Table) {
        return dao.upsertTable(table)
    }

    override suspend fun deleteTable(id: Int) {
        return dao.deleteTable(id)
    }

    override suspend fun upsertHistory(
        name: String,
        species: String,
        location: String,
        date: String,
        note: String
    ) {
        val str = "$name / $species / $location / $date / $note / $hour:$min"
        val history = History(str = str)
        return dao.upsertHistory(history)
    }

    override suspend fun getAllHistory(): Flow<List<History>> {
        return dao.getAllHistory()
    }
}