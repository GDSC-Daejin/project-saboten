package commonClient.presentation

import common.model.reseponse.post.Post
import commonClient.data.LoadState
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.usecase.post.GetPostByIdUseCase
import commonClient.presentation.PostDetailScreenViewModelDelegate.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


interface PostDetailScreenViewModelDelegate : UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val postState: LoadState<Post> = LoadState.loading()
    )

    sealed interface Effect

    sealed interface Event {
        class SetPost(val post: Post) : Event
        class LoadPost(val postId: Long) : Event
    }

}

@Singleton
class PostDetailScreenViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase
) : PlatformViewModel(), PostDetailScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)

    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val postState = MutableStateFlow<LoadState<Post>>(LoadState.loading())

    override val state: StateFlow<State>
        get() = TODO("Not yet implemented")

    override fun event(e: Event) {
        when (e) {
            is Event.LoadPost -> {
                getPostByIdUseCase(e.postId)
                    .onEach { postState.emit(it) }
                    .launchIn(platformViewModelScope)
            }
            is Event.SetPost -> {
                postState.tryEmit(LoadState.success(e.post))
            }
        }
    }
}
