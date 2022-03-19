package commonClient.presentation

import common.model.reseponse.user.UserInfo
import commonClient.di.Inject
import commonClient.domain.usecase.user.ObserveMeUseCase
import commonClient.presentation.AppViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


interface AppViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val me: UserInfo? = null,
    )

    sealed class Effect {

    }

    sealed class Event {

    }

}

class AppViewModel @Inject constructor(
    observeMeUseCase: ObserveMeUseCase
) : PlatformViewModel(), AppViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val me = observeMeUseCase()

    override val state: StateFlow<State> = me.map {
        State(me = it)
    }.distinctUntilChanged().asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
            }
        }
    }

}