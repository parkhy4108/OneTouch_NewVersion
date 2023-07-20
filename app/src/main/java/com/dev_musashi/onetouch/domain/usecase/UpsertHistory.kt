package com.dev_musashi.onetouch.domain.usecase

import com.dev_musashi.onetouch.domain.model.History
import com.dev_musashi.onetouch.domain.model.Table
import com.dev_musashi.onetouch.domain.repository.Repository
import javax.inject.Inject

class UpsertHistory @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(
        name: String,
        species: String,
        location: String,
        date: String,
        note: String
    ) {
        return repository.upsertHistory(name, species, location, date, note)
    }
}
