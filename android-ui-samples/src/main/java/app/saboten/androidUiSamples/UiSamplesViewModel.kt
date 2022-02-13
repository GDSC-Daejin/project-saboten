package app.saboten.androidUiSamples

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class ThemeState {
    Light,
    Dark
}

class UiSamplesViewModel : ViewModel() {

    private val themeState = MutableStateFlow(ThemeState.Light)
    val themeStateFlow = themeState.asStateFlow()

    fun toggleTheme() {
        viewModelScope.launch {
            themeState.emit(
                when (themeState.value) {
                    ThemeState.Light -> ThemeState.Dark
                    ThemeState.Dark -> ThemeState.Light
                }
            )
        }
    }

}