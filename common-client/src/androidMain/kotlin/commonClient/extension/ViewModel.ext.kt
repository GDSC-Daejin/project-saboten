package commonClient.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import commonClient.presentation.UnidirectionalViewModelDelegate
import kotlinx.coroutines.flow.Flow

actual data class ViewModelComponent<S, EF, E> constructor(
    val state: S,
    val effect: Flow<EF>,
    val dispatch: (E) -> Unit
)

@Composable
actual fun <S : Any, EF, E> UnidirectionalViewModelDelegate<S, EF, E>.extract(): ViewModelComponent<S?, EF, E> {

    val state by state.collectAsState()

    val dispatch: (E) -> Unit = { event -> event(event) }

    return ViewModelComponent(
        state = state,
        effect = effect,
        dispatch = dispatch
    )
}