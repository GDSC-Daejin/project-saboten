package commonClient.presentation

import kotlinx.coroutines.CoroutineScope

expect open class PlatformViewModel() {

    protected val platformViewModelScope : CoroutineScope

    open fun onViewModelCleared()

}