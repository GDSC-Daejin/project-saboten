package commonClient.presentation

import common.model.reseponse.user.User
import commonClient.di.Inject
import commonClient.domain.usecase.user.GetMe
import commonClient.presentation.HomeScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface HomeScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val me: User? = null,
    )

    sealed class Effect {

    }

    sealed class Event {
        object ReloadContents : Event()
    }

}

class HomeScreenViewModel @Inject constructor(
    getMe: GetMe
) : PlatformViewModel(), HomeScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<State> = getMe().map {
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