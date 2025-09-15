package com.yunho.feature.home.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.yunho.feature.home.intent.HomeEvent

@Composable
fun HomeDialog(
    dialogState: HomeDialogState,
    onDismissRequest: () -> Unit,
    onEvent: (HomeEvent) -> Unit
) {
    when {
        dialogState.content != null -> Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties()
        ) {
             when (val content = dialogState.content) {
                is HomeDialogState.Content.Default -> Default(content = content, onEvent = onEvent)
                null -> Unit
             }
        }

        else -> Unit
    }
}

@Composable
private fun Default(
    content: HomeDialogState.Content.Default,
    onEvent: (HomeEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 24.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            
        }
    }
}