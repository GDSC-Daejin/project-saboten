package app.saboten.commonClient.presentation

import kotlinx.coroutines.CoroutineScope

expect open class PlatformViewModel() {

    protected val platformViewModelScope : CoroutineScope

    protected fun onCleared()

}