package com.dev_musashi.onetouch.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dev_musashi.onetouch.domain.model.Table


@Database(
    entities = [Table::class],
    version = 1,
    exportSchema = false
)
abstract class DataBase : RoomDatabase() {
    companion object{
        const val DB_NAME = "tableDB"
    }
    abstract val dao : Dao
}