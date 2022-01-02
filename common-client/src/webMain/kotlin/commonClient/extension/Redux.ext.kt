package commonClient.extension

import commonClient.presentation.jsCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import react.useEffect
import react.useLayoutEffect

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
