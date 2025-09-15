package com.yunho.feature.base

import androidx.lifecycle.ViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

abstract class BaseViewModel<STATE : Any, SIDE_EFFECT : Any>(initialState: STATE) : ContainerHost<STATE, SIDE_EFFECT>, ViewModel() {
    override val container: Container<STATE, SIDE_EFFECT> = container(initialState)
}
