package com.dev_musashi.onetouch.domain.repository

import com.dev_musashi.onetouch.domain.model.Table
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getTables(): Flow<List<Table>>
    suspend fun getTable(id: Int) : Table
    suspend fun upsertTable(table: Table)
    suspend fun deleteTable(table: Table)
}