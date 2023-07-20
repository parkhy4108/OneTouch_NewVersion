package com.dev_musashi.onetouch.domain.usecase

data class UseCases(
    val getAllTables: GetAllTables,
    val getAllHistory: GetAllHistory,
    val getTableIdAndTitle: GetTableIdAndTitle,
    val getTable: GetTable,
    val upsertTable: UpsertTable,
    val upsertHistory: UpsertHistory,
    val deleteTable: DeleteTable,
    val saveImage: SaveImage
)
