package com.dev_musashi.onetouch.domain.usecase

import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTables @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Table>> {
        return repository.getAllTables()
    }
}