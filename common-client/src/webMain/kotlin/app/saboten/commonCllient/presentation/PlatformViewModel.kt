package app.saboten.commonCllient.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.cancel

actual open class PlatformViewModel {
    protected actual val platformViewModelScope: CoroutineScope get() = CoroutineScope(Default)
    protected actual fun onCleared() {
        platformViewModelScope.cancel()
    }
}