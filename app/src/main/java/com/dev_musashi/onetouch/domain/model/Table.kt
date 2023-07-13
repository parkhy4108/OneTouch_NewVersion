package com.dev_musashi.onetouch.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table")
data class Table(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val title: String,
    val name: String = "",
    val species: String = "",
    val location: String = "",
    val date: String = "",
    val note: String = "",
)
