package commonClient.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

actual open class PlatformViewModel {
    protected actual val platformViewModelScope: CoroutineScope get() = jsCoroutineScope
    actual open fun onViewModelCleared() {
        platformViewModelScope.cancel()
    }
}