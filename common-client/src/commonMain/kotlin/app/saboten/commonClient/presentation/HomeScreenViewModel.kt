package app.saboten.commonClient.presentation

import app.saboten.common.entities.Post
import app.saboten.common.entities.User
import app.saboten.commonClient.data.LoadState
import app.saboten.commonClient.domain.usecase.post.GetPosts
import app.saboten.commonClient.domain.usecase.user.GetMe
import app.saboten.commonClient.presentation.HomeScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface HomeScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val posts: LoadState<List<Post>> = LoadState.loading(),
        val me: LoadState<User> = LoadState.loading(),
    )

    sealed class Effect {

    }

    sealed class Event {
        object ReloadContents : Event()
    }

}

class HomeScreenViewModel(
    getMe: GetMe,
    getPosts: GetPosts
) : PlatformViewModel(), HomeScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val reloadTrigger = MutableStateFlow(Unit)

    override val state: StateFlow<State> = reloadTrigger
        .flatMapConcat {
            combine(
                getPosts(),
                getMe()
            ) { posts, me -> State(posts, me) }
        }.asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        platformViewModelScope.launch {
            when (e) {
                Event.ReloadContents -> reloadTrigger.emit(Unit)
            }
        }
    }

}