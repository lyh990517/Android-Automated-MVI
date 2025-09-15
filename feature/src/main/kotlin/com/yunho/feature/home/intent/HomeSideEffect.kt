package com.yunho.feature.home.intent

import com.yunho.feature.home.view.component.HomeDialogState

sealed interface HomeSideEffect {
    sealed interface Navigate : HomeSideEffect {
        data object Back : Navigate
    }

    sealed interface Show : HomeSideEffect {
        data class Dialog(val content: HomeDialogState.Content) : Show
    }

    sealed interface Hide : HomeSideEffect {
        data object Dialog : Hide
    }
}