package com.dev_musashi.onetouch.domain.usecase

import com.dev_musashi.onetouch.domain.model.History
import com.dev_musashi.onetouch.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHistory @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(): Flow<List<History>> {
        return repository.getAllHistory()
    }
}
