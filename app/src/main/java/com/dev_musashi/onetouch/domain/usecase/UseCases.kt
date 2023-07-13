package com.dev_musashi.onetouch.domain.usecase

data class UseCases(
    val getTables: GetTables,
    val getTableIdAndTitle: GetTableIdAndTitle,
    val getTable: GetTable,
    val upsertTable: UpsertTable,
    val deleteTable: DeleteTable
)
