package com.dev_musashi.onetouch.data.data_source

import androidx.room.Query
import androidx.room.Dao
import androidx.room.Upsert
import com.dev_musashi.onetouch.domain.model.History
import com.dev_musashi.onetouch.domain.model.TitleButton
import com.dev_musashi.onetouch.domain.model.Table
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Query("SELECT*FROM `table` ORDER BY timestamp DESC")
    fun getTables(): Flow<List<Table>>

    @Query("SELECT id, title, timestamp FROM 'table' ORDER BY timestamp DESC")
    fun getTablesIdAndTitle(): Flow<List<TitleButton>>

    @Query("SELECT * FROM `table` WHERE id= :id")
    suspend fun getTable(id: Int) : Table

    @Upsert
    suspend fun upsertTable(table: Table)

    @Query("DELETE FROM `table` WHERE id = :id")
    suspend fun deleteTable(id: Int)

    @Upsert
    suspend fun upsertHistory(history: History)

    @Query("SELECT * FROM `history` ORDER BY id DESC")
    fun getAllHistory() : Flow<List<History>>
}