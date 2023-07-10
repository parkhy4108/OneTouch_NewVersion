package com.dev_musashi.onetouch.di

import android.app.Application
import androidx.room.Room
import com.dev_musashi.onetouch.data.data_source.DataBase
import com.dev_musashi.onetouch.data.repository.RepositoryImpl
import com.dev_musashi.onetouch.domain.repository.Repository
import com.dev_musashi.onetouch.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): DataBase {
        return Room.databaseBuilder(
            app,
            DataBase::class.java,
            DataBase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(dataBase: DataBase) : Repository {
        return RepositoryImpl(dataBase.dao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: Repository) : UseCases {
        return UseCases(
            getTables = GetTables(repository),
            getTable = GetTable(repository),
            insertTable = InsertTable(repository),
            deleteTable = DeleteTable(repository)
        )
    }

}