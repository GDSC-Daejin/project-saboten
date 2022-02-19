package commonClient.presentation

import common.model.User
import commonClient.data.LoadState
import commonClient.domain.usecase.user.GetMe
import commonClient.presentation.HomeScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface HomeScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val me: LoadState<User> = LoadState.loading(),
    )

    sealed class Effect {

    }

    sealed class Event {
        object ReloadContents : Event()
    }

}

class HomeScreenViewModel(
    getMe: GetMe
) : PlatformViewModel(), HomeScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<State>
        get() = TODO("Not yet implemented")

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
                Event.ReloadContents -> {
                    
                }
            }
        }
    }

}