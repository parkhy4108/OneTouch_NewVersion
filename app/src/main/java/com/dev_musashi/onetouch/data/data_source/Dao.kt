package com.dev_musashi.onetouch.data.data_source

import androidx.room.Query
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Upsert
import com.dev_musashi.onetouch.domain.model.Table
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Query("SELECT*FROM `table`")
    fun getTables(): Flow<List<Table>>

    @Query("SELECT * FROM `table` WHERE id= :id")
    suspend fun getTable(id: Long) : Table

    @Upsert
    suspend fun insertTable(table: Table)

    @Delete
    suspend fun deleteTable(table: Table)

}