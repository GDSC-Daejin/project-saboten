package commonClient.presentation

import common.model.reseponse.user.UserInfo
import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.entity.AppTheme
import commonClient.domain.usecase.settings.ObserveAppThemeSettingsUseCase
import commonClient.domain.usecase.user.ObserveMeUseCase
import commonClient.presentation.AppViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


interface AppViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val me: UserInfo? = null,
        val appTheme: AppTheme = AppTheme.SYSTEM
    )

    sealed class Effect {

    }

    sealed class Event {

    }

}

@HiltViewModel
class AppViewModel @Inject constructor(
    observeMeUseCase: ObserveMeUseCase,
    observeAppThemeSettingsUseCase: ObserveAppThemeSettingsUseCase
) : PlatformViewModel(), AppViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<State> = combine(
        observeMeUseCase(),
        observeAppThemeSettingsUseCase(),
    ) { me, appTheme ->
        State(
            me = me,
            appTheme = appTheme ?: AppTheme.SYSTEM
        )
    }.distinctUntilChanged().asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        platformViewModelScope.launch {

        }
    }

}