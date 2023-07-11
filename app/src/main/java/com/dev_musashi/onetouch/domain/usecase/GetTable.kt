package com.dev_musashi.onetouch.domain.usecase

import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.repository.Repository
import javax.inject.Inject

class GetTable @Inject constructor(
    private val repository: Repository
){
    suspend operator fun invoke(id: Int): Table {
        return repository.getTable(id)
    }
}