package com.dev_musashi.onetouch.domain.usecase

data class UseCases(
    val getTables: GetTables,
    val getTable: GetTable,
    val insertTable: InsertTable,
    val deleteTable: DeleteTable
)
