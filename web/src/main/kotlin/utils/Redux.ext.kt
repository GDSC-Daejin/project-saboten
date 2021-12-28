package utils

import app.saboten.commonCllient.presentation.UnidirectionalViewModelDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import react.StateInstance
import react.useEffect
import react.useState

fun useLaunchedEffect(block: suspend CoroutineScope.() -> Unit) {
    useEffect {
        CoroutineScope(Dispatchers.Default).launch(block = block)
    }
}

fun <S : Any, EF, E> UnidirectionalViewModelDelegate<S, EF, E>.extract() : ReduxViewModelComponent<S?, EF, E> {
    val reduxState = useState<S>()

    useLaunchedEffect {
        state.collectLatest {
            reduxState.component2().invoke(it)
        }
    }

    return ReduxViewModelComponent(reduxState, effect, ::event)
}

data class ReduxViewModelComponent<S, EF, E>(
    val state: StateInstance<S>,
    val effect: Flow<EF>,
    val dispatch: (E) -> Unit
)