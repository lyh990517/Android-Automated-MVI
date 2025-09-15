package com.yunho.feature.home.view.model

import com.yunho.feature.base.BaseViewModel
import com.yunho.feature.home.intent.HomeEvent
import com.yunho.feature.home.intent.HomeSideEffect
import com.yunho.feature.home.intent.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    // TODO: 의존성 주입 파라미터
) : BaseViewModel<HomeState, HomeSideEffect>(initialState = HomeState()) {

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Click -> onClick(event)
        }
    }

    private fun onClick(event: HomeEvent.Click) {
        intent {
            when (event) {
                HomeEvent.Click.Back -> postSideEffect(HomeSideEffect.Navigate.Back)
            }
        }
    }
}