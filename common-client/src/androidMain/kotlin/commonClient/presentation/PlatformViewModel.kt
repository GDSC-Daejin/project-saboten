package commonClient.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual open class PlatformViewModel : ViewModel() {

    protected actual val platformViewModelScope: CoroutineScope = viewModelScope

    actual open fun onViewModelCleared() {
    }

    override fun onCleared() {
        super.onCleared()
        onViewModelCleared()
    }

}