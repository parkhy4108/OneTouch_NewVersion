package com.dev_musashi.onetouch.presentation.common.snackBar

import androidx.annotation.StringRes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SnackBarManager {
    private val messages: MutableStateFlow<SnackBarMessage?> = MutableStateFlow(null)
    val snackMessage: StateFlow<SnackBarMessage?> get() = messages.asStateFlow()

    fun showMessage(@StringRes message: Int) {
        messages.value = SnackBarMessage.ResourceSnackBar(message)
    }

    fun showMessage(message: SnackBarMessage) {
        messages.value = message
    }

    fun initMessage() {
        messages.value = null
    }
}
