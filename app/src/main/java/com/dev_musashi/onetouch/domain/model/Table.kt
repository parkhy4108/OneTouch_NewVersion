package com.dev_musashi.onetouch.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table")
data class Table(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    val title: String,

    val name: String,
    val nameContent: String,

    val species: String,
    val speciesContent: String,

    val location: String,
    val locationContent: String,

    val date: String,
    val dateContent: String,

    val empty: String,
    val emptyContent: String,

    val timestamp: Long
)
