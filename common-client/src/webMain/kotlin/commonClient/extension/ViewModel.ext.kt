package commonClient.extension

import commonClient.presentation.UnidirectionalViewModelDelegate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import react.useState

actual data class ViewModelComponent<S, EF, E> constructor(
    val state: S?,
    val effect: Flow<EF>,
    val dispatch: (E) -> Unit
)

actual fun <S : Any, EF, E> UnidirectionalViewModelDelegate<S, EF, E>.extract(): ViewModelComponent<S, EF, E> {
    val (reduxState, setReduxState) = useState<S>()

    useLaunchedLayoutEffect {
        state.onEach { setReduxState(it) }.launchIn(this)
    }

    return ViewModelComponent(reduxState, effect, ::event)
}