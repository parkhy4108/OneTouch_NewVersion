package com.dev_musashi.onetouch.data.repository

import com.dev_musashi.onetouch.data.data_source.Dao
import com.dev_musashi.onetouch.domain.model.History
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.model.Title
import com.dev_musashi.onetouch.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: Dao
) : Repository {

    override fun getTableIdAndTitle(): Flow<List<Title>> {
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
        val cal: Calendar = Calendar.getInstance()
        val hour = SimpleDateFormat("HH", Locale("ko", "KR")).format(cal.time)
        val min = SimpleDateFormat("mm", Locale("ko", "KR")).format(cal.time)
        val str = "$name / $species / $location / $date / $note / $hour : $min"
        val history = History(str = str)
        return dao.upsertHistory(history)
    }

    override fun getAllHistory(): Flow<List<History>> {
        return dao.getAllHistory()
    }
}