package app.saboten.commonClient.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

interface UnidirectionalViewModelDelegate<S: Any, EF, E> {

    fun Flow<S>.asStateFlow(
        defaultValue : S,
        coroutineScope : CoroutineScope
    ) : StateFlow<S> = stateIn(
        coroutineScope,
        SharingStarted.Lazily,
        defaultValue
    )

    val effect: Flow<EF>
    val state: StateFlow<S>
    fun event(e: E)

}

data class PresentationComponent<S, EF, E>(
    val state: S,
    val effect: Flow<EF>,
    val dispatch: (E) -> Unit
)