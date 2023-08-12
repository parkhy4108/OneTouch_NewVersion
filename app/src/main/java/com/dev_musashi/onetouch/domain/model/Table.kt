package com.dev_musashi.onetouch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table")
data class Table(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val timestamp: Long,
    val title: String,
    val name: String = "공사명",
    val species: String = "공종",
    val location: String = "위치",
    val date: String = "날짜",
    val note: String = "비고",
    val nameContent: String = "",
    val speciesContent: String = "",
    val locationContent: String = "",
    val dateContent: String = "",
    val noteContent: String = ""

)
