package commonClient.presentation

import common.model.reseponse.user.UserInfo
import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.entity.AppTheme
import commonClient.domain.usecase.settings.ObserveAppThemeSettingsUseCase
import commonClient.domain.usecase.settings.UpdateAppThemeSettingsUseCase
import commonClient.domain.usecase.user.GetMeUseCase
import commonClient.presentation.HomeScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface HomeScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val me: UserInfo? = null,
    )

    sealed class Effect {

    }

    sealed class Event {
        object ReloadContents : Event()
    }

}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    getMeUseCase: GetMeUseCase
) : PlatformViewModel(), HomeScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<State> = getMeUseCase().map {
        State(me = it.getDataOrNull())
    }.distinctUntilChanged().asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
                Event.ReloadContents -> {
                    
                }
            }
        }
    }

}