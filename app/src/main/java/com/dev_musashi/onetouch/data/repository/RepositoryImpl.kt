package com.dev_musashi.onetouch.data.repository

import com.dev_musashi.onetouch.data.data_source.Dao
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: Dao
) : Repository {

    override fun getTables(): Flow<List<Table>> {
        return dao.getTables()
    }

    override suspend fun getTable(id: Long): Table {
        return dao.getTable(id)
    }

    override suspend fun insertTable(table: Table) {
        dao.insertTable(table)
    }

    override suspend fun deleteTable(table: Table) {
        dao.deleteTable(table)
    }
}