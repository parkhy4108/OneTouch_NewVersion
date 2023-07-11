package com.dev_musashi.onetouch.domain.usecase

data class UseCases(
    val getTables: GetTables,
    val getTable: GetTable,
    val upsertTable: UpsertTable,
    val deleteTable: DeleteTable
)
