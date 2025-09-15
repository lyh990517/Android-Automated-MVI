package convention.plugin.mvi

import java.io.File

sealed interface Mvi {
    val configuration: Configuration

    fun create()

    data class State(
        override val configuration: Configuration
    ) : Mvi {
        override fun create() {
            with(configuration) {
                File(intentDir, "${featureName}State.kt").writeText(
                    """
                package $basePackage.$featurePath.intent

                data class ${featureName}State(
                    val data: String
                ) {
                    constructor() : this(data = "")
                }
                """.trimIndent()
                )
            }
        }
    }

    data class SideEffect(
        override val configuration: Configuration
    ) : Mvi {
        override fun create() {
            with(configuration) {
                File(intentDir, "${featureName}SideEffect.kt").writeText(
                    """
                package $basePackage.$featurePath.intent

                import $basePackage.$featurePath.view.component.${featureName}DialogState

                sealed interface ${featureName}SideEffect {
                    sealed interface Navigate : ${featureName}SideEffect {
                        data object Back : Navigate
                    }

                    sealed interface Show : ${featureName}SideEffect {
                        data class Dialog(val content: ${featureName}DialogState.Content) : Show
                    }

                    sealed interface Hide : ${featureName}SideEffect {
                        data object Dialog : Hide
                    }
                }
                """.trimIndent()
                )
            }
        }
    }

    data class Event(
        override val configuration: Configuration
    ) : Mvi {
        override fun create() {
            with(configuration) {
                File(intentDir, "${featureName}Event.kt").writeText(
                    """
                package $basePackage.$featurePath.intent

                sealed interface ${featureName}Event {
                    sealed interface Click : ${featureName}Event {
                        data object Back : Click
                    }
                }
                """.trimIndent()
                )
            }
        }
    }

    data class Ui(
        override val configuration: Configuration
    ) : Mvi {
        override fun create() {
            with(configuration) {
                File(viewDir, "${featureName}.kt").writeText(
                    """
                package $basePackage.$featurePath.view

                import androidx.compose.foundation.layout.Column
                import androidx.compose.material3.Button
                import androidx.compose.material3.Text
                import androidx.compose.runtime.Composable
                import androidx.compose.runtime.getValue
                import androidx.compose.ui.Modifier
                import androidx.hilt.navigation.compose.hiltViewModel
                import $basePackage.navigation.LocalNavController
                import $basePackage.$featurePath.intent.${featureName}Event
                import $basePackage.$featurePath.intent.${featureName}SideEffect
                import $basePackage.$featurePath.intent.${featureName}State
                import $basePackage.$featurePath.view.component.${featureName}Dialog
                import $basePackage.$featurePath.view.component.${featureName}DialogState.Companion.remember${featureName}DialogState
                import $basePackage.$featurePath.view.model.${featureName}ViewModel
                import org.orbitmvi.orbit.compose.collectAsState
                import org.orbitmvi.orbit.compose.collectSideEffect

                @Composable
                fun ${featureName}(
                    modifier: Modifier = Modifier,
                    viewModel: ${featureName}ViewModel = hiltViewModel()
                ) {
                    val state by viewModel.collectAsState()
                    val dialogState = remember${featureName}DialogState()
                    val navController = LocalNavController.current

                    viewModel.collectSideEffect { effect ->
                        when (effect) {
                            ${featureName}SideEffect.Navigate.Back -> navController.popBackStack()
                            ${featureName}SideEffect.Hide.Dialog -> dialogState.hide()
                            is ${featureName}SideEffect.Show.Dialog -> dialogState.show(effect.content)
                        }
                    }

                    ${featureName}Dialog(
                        dialogState = dialogState,
                        onDismissRequest = dialogState::hide,
                        onEvent = viewModel::onEvent
                    )

                    ${featureName}(
                        state = state,
                        onEvent = viewModel::onEvent,
                        modifier = modifier
                    )
                }

                @Composable
                fun ${featureName}(
                    state: ${featureName}State,
                    onEvent: (${featureName}Event) -> Unit,
                    modifier: Modifier = Modifier
                ) {
                    Column(modifier = modifier) {
                        Button({
                            onEvent(${featureName}Event.Click.Back)
                        }) {
                            Text("$featureName ${"$"}{state.data}")
                        }
                    }
                }
                """.trimIndent()
                )
            }
        }
    }

    data class ViewModel(
        override val configuration: Configuration
    ) : Mvi {
        override fun create() {
            with(configuration) {
                File(viewModelDir, "${featureName}ViewModel.kt").writeText(
                    """
                package $basePackage.$featurePath.view.model

                import $basePackage.base.BaseViewModel
                import $basePackage.$featurePath.intent.${featureName}Event
                import $basePackage.$featurePath.intent.${featureName}SideEffect
                import $basePackage.$featurePath.intent.${featureName}State
                import dagger.hilt.android.lifecycle.HiltViewModel
                import javax.inject.Inject

                @HiltViewModel
                class ${featureName}ViewModel @Inject constructor(
                    // TODO: 의존성 주입 파라미터
                ) : BaseViewModel<${featureName}State, ${featureName}SideEffect>(initialState = ${featureName}State()) {

                    fun onEvent(event: ${featureName}Event) {
                        when (event) {
                            is ${featureName}Event.Click -> onClick(event)
                        }
                    }

                    private fun onClick(event: ${featureName}Event.Click) {
                        intent {
                            when (event) {
                                ${featureName}Event.Click.Back -> postSideEffect(${featureName}SideEffect.Navigate.Back)
                            }
                        }
                    }
                }
                """.trimIndent()
                )
            }
        }
    }

    data class Dialog(
        override val configuration: Configuration
    ) : Mvi {
        override fun create() {
            with(configuration) {
                File(componentDir, "${featureName}Dialog.kt").writeText(
                    """
                package $basePackage.$featurePath.view.component

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
                import $basePackage.$featurePath.intent.${featureName}Event

                @Composable
                fun ${featureName}Dialog(
                    dialogState: ${featureName}DialogState,
                    onDismissRequest: () -> Unit,
                    onEvent: (${featureName}Event) -> Unit
                ) {
                    when {
                        dialogState.content != null -> Dialog(
                            onDismissRequest = onDismissRequest,
                            properties = DialogProperties()
                        ) {
                             when (val content = dialogState.content) {
                                is ${featureName}DialogState.Content.Default -> Default(content = content, onEvent = onEvent)
                                null -> Unit
                             }
                        }

                        else -> Unit
                    }
                }

                @Composable
                private fun Default(
                    content: ${featureName}DialogState.Content.Default,
                    onEvent: (${featureName}Event) -> Unit,
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
                """.trimIndent()
                )
            }
        }
    }

    data class DialogState(
        override val configuration: Configuration
    ) : Mvi {
        override fun create() {
            with(configuration) {
                File(componentDir, "${featureName}DialogState.kt").writeText(
                    """
                package $basePackage.$featurePath.view.component

                import androidx.compose.runtime.Composable
                import androidx.compose.runtime.getValue
                import androidx.compose.runtime.mutableStateOf
                import androidx.compose.runtime.remember
                import androidx.compose.runtime.setValue

                class ${featureName}DialogState {
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
                        fun remember${featureName}DialogState() = remember { ${featureName}DialogState() }
                    }
                }
                """.trimIndent()
                )
            }
        }
    }

    companion object {
        fun create(configuration: Configuration) {
            with(configuration) {
                println("  📝 Creating ${featureName}State.kt...")
                State(this).create()

                println("  📝 Creating ${featureName}SideEffect.kt...")
                SideEffect(this).create()

                println("  📝 Creating ${featureName}Event.kt...")
                Event(this).create()

                println("  📝 Creating ${featureName}.kt (UI)...")
                Ui(this).create()

                println("  📝 Creating ${featureName}ViewModel.kt...")
                ViewModel(this).create()

                println("  📝 Creating ${featureName}Dialog.kt...")
                Dialog(this).create()

                println("  📝 Creating ${featureName}DialogState.kt...")
                DialogState(this).create()
            }
        }
    }
}

