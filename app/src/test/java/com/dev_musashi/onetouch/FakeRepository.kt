package com.dev_musashi.onetouch

import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeRepository : Repository {
    val tables = mutableListOf<Table>()

    override fun getAllTables(): Flow<List<Table>> = flow { emit(tables) }

    override suspend fun getTable(id: Long): Table {
        return tables.find { it.id == id }!!
    }

    override suspend fun upsertTable(table: Table) {
        tables.add(table)
    }

    override suspend fun deleteTable(table: Table) {
        tables.remove(table)
    }
}