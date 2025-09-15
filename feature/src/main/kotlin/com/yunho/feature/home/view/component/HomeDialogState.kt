package com.yunho.feature.home.view.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class HomeDialogState {
    var content by mutableStateOf<Content?>(null)

    fun hide() {
        content = null
    }

    fun show(content: Content) {
        this.content = content
    }

    sealed interface Content {
        data object Default : Content
    }

    companion object {
        @Composable
        fun rememberHomeDialogState() = remember { HomeDialogState() }
    }
}