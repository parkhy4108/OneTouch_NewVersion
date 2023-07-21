package com.dev_musashi.onetouch.domain.usecase

import com.dev_musashi.onetouch.domain.model.Title
import com.dev_musashi.onetouch.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTableIdAndTitle @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Title>> {
        return repository.getTableIdAndTitle()
    }
}