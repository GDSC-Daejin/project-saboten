package commonClient.presentation

import kotlinx.coroutines.CoroutineScope

expect open class PlatformViewModel() {

    val platformViewModelScope : CoroutineScope

    open fun onViewModelCleared()

}