package com.yunho.feature.home.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.yunho.feature.navigation.LocalNavController
import com.yunho.feature.home.intent.HomeEvent
import com.yunho.feature.home.intent.HomeSideEffect
import com.yunho.feature.home.intent.HomeState
import com.yunho.feature.home.view.component.HomeDialog
import com.yunho.feature.home.view.component.HomeDialogState.Companion.rememberHomeDialogState
import com.yunho.feature.home.view.model.HomeViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.collectAsState()
    val dialogState = rememberHomeDialogState()
    val navController = LocalNavController.current

    viewModel.collectSideEffect { effect ->
        when (effect) {
            HomeSideEffect.Navigate.Back -> navController.popBackStack()
            HomeSideEffect.Hide.Dialog -> dialogState.hide()
            is HomeSideEffect.Show.Dialog -> dialogState.show(effect.content)
        }
    }

    HomeDialog(
        dialogState = dialogState,
        onDismissRequest = dialogState::hide,
        onEvent = viewModel::onEvent
    )

    Home(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
fun Home(
    state: HomeState,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Button({
            onEvent(HomeEvent.Click.Back)
        }) {
            Text("Home ${state.data}")
        }
    }
}