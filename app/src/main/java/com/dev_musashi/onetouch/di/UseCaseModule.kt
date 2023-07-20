package com.dev_musashi.onetouch.di

import com.dev_musashi.onetouch.domain.gallery.Gallery
import com.dev_musashi.onetouch.domain.repository.Repository
import com.dev_musashi.onetouch.domain.usecase.DeleteTable
import com.dev_musashi.onetouch.domain.usecase.GetAllHistory
import com.dev_musashi.onetouch.domain.usecase.GetAllTables
import com.dev_musashi.onetouch.domain.usecase.GetTable
import com.dev_musashi.onetouch.domain.usecase.GetTableIdAndTitle
import com.dev_musashi.onetouch.domain.usecase.SaveImage
import com.dev_musashi.onetouch.domain.usecase.UpsertHistory
import com.dev_musashi.onetouch.domain.usecase.UpsertTable
import com.dev_musashi.onetouch.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository, gallery: Gallery) : UseCases {
        return UseCases(
            getAllTables = GetAllTables(repository),
            getTableIdAndTitle = GetTableIdAndTitle(repository),
            getTable = GetTable(repository),
            upsertTable = UpsertTable(repository),
            deleteTable = DeleteTable(repository),
            getAllHistory = GetAllHistory(repository),
            upsertHistory = UpsertHistory(repository),
            saveImage = SaveImage(gallery)
        )
    }

}