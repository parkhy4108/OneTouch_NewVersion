package com.dev_musashi.onetouch.domain.repository

import com.dev_musashi.onetouch.domain.model.TitleButton
import com.dev_musashi.onetouch.domain.model.Table
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getTables(): Flow<List<Table>>

    fun getTableIdAndTitle(): Flow<List<TitleButton>>

    suspend fun getTable(id: Int) : Table
    suspend fun upsertTable(table: Table)
    suspend fun deleteTable(id: Int)
}