package utils

import commonClient.presentation.UnidirectionalViewModelDelegate
import commonClient.presentation.jsCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import react.useEffect
import react.useLayoutEffect
import react.useState

fun useLaunchedEffect(
    block: suspend CoroutineScope.() -> Unit
) {
    useEffect {
        val job = jsCoroutineScope.launch(block = block)
        cleanup {
            job.cancel()
        }
    }
}

fun useLaunchedEffect(
    vararg dependencies: dynamic,
    block: suspend CoroutineScope.() -> Unit
) {
    useEffect(*dependencies) {
        val job = jsCoroutineScope.launch(block = block)
        cleanup {
            job.cancel()
        }
    }
}

fun useLaunchedLayoutEffect(
    block: suspend CoroutineScope.() -> Unit
) {
    useLayoutEffect {
        val job = jsCoroutineScope.launch(block = block)
        cleanup {
            job.cancel()
        }
    }
}

fun useLaunchedLayoutEffect(
    vararg dependencies: dynamic,
    block: suspend CoroutineScope.() -> Unit
) {
    useLayoutEffect(*dependencies) {
        val job = jsCoroutineScope.launch(block = block)
        cleanup {
            job.cancel()
        }
    }
}

fun <S : Any, EF, E> UnidirectionalViewModelDelegate<S, EF, E>.extract(): ReduxViewModelComponent<S?, EF, E> {
    val (reduxState, setReduxState) = useState<S>()

    useLaunchedLayoutEffect {
        state.onEach { setReduxState(it) }.launchIn(this)
    }

    return ReduxViewModelComponent(reduxState, effect, ::event)
}

data class ReduxViewModelComponent<S, EF, E>(
    val state: S?,
    val effect: Flow<EF>,
    val dispatch: (E) -> Unit
)