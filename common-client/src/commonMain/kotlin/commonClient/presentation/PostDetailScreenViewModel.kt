package commonClient.presentation

import common.model.reseponse.post.Post
import commonClient.data.LoadState
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.presentation.PostDetailScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow


interface PostDetailScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val postState : LoadState<Post> = LoadState.loading()
    )

    sealed interface Effect

    sealed interface Event

}

@Singleton
class PostDetailScreenViewModel @Inject constructor(

) : PlatformViewModel(), PostDetailScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)

    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    override val state: StateFlow<State>
        get() = TODO("Not yet implemented")

    override fun event(e: Event) {
        TODO("Not yet implemented")
    }
}
