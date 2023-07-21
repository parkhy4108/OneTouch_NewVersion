package com.dev_musashi.onetouch.domain.repository

import com.dev_musashi.onetouch.domain.model.History
import com.dev_musashi.onetouch.domain.model.Title
import com.dev_musashi.onetouch.domain.model.Table
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getTableIdAndTitle(): Flow<List<Title>>
    suspend fun getTable(id: Int): Table
    suspend fun upsertTable(table: Table)
    suspend fun deleteTable(id: Int)
    suspend fun upsertHistory(
        name: String,
        species: String,
        location: String,
        date: String,
        note: String
    )
    suspend fun getAllHistory(): Flow<List<History>>
}