package app.saboten.commonCllient.presentation

import kotlinx.coroutines.CoroutineScope

expect open class PlatformViewModel() {

    protected val platformViewModelScope : CoroutineScope

    protected fun onCleared()

}