package com.yunho.feature.home.intent

sealed interface HomeEvent {
    sealed interface Click : HomeEvent {
        data object Back : Click
    }
}