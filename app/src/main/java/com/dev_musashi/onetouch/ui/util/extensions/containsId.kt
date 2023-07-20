package com.dev_musashi.onetouch.ui.util.extensions

import com.dev_musashi.onetouch.domain.model.TitleButton

fun List<TitleButton>.containsId(
    id: Int
) : Boolean {
    var result = false
    this.forEach { table ->
        if(table.id == id) result = true
    }
    return result
}