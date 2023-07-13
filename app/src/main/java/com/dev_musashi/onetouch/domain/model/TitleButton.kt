package com.dev_musashi.onetouch.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TitleButton(
    val id: Int,
    val title: String,
    val timestamp: Long
): Parcelable
