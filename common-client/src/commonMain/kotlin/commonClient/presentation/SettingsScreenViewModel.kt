package commonClient.presentation

import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.entity.settings.AppTheme
import commonClient.domain.usecase.settings.ObserveAppThemeSettingsUseCase
import commonClient.domain.usecase.settings.UpdateAppThemeSettingsUseCase
import commonClient.presentation.SettingsScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface SettingsScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val appTheme: AppTheme = AppTheme.SYSTEM
    )

    sealed class Effect {

    }

    sealed class Event {
        class ChangeTheme(val appTheme: AppTheme) : Event()
    }

}

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    observeAppThemeSettingsUseCase: ObserveAppThemeSettingsUseCase,
    private val updateAppThemeSettingsUseCase: UpdateAppThemeSettingsUseCase,
) : PlatformViewModel(), SettingsScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<State> =
        observeAppThemeSettingsUseCase().map {
            State(appTheme = it)
        }.asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
                is Event.ChangeTheme -> updateAppThemeSettingsUseCase(e.appTheme)
            }
        }
    }

}