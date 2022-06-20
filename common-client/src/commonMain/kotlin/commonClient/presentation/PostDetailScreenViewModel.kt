package commonClient.presentation

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import common.model.reseponse.post.CommentResponse
import common.model.reseponse.post.PostResponse
import commonClient.data.LoadState
import commonClient.data.isSuccess
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.entity.post.Comment
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.post.GetPostByIdUseCase
import commonClient.domain.usecase.post.comment.GetPagedCommentForPostUseCase
import commonClient.presentation.PostDetailScreenViewModelDelegate.*
import commonClient.utils.onlyAtSuccess
import commonClient.utils.toLoadState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


interface PostDetailScreenViewModelDelegate :
    UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val postState: LoadState<Post> = LoadState.loading(),
        val postComments: Flow<PagingData<Comment>> = flowOf()
    )

    sealed interface Effect

    sealed interface Event {
        class SetPost(val post: Post) : Event
        class LoadPost(val postId: Long) : Event
        object Refresh : Event
    }

}

@Singleton
class PostDetailScreenViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getCommentForPostUseCase: GetPagedCommentForPostUseCase
) : PlatformViewModel(), PostDetailScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)

    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val postState = MutableStateFlow<LoadState<Post>>(LoadState.loading())

    /*
    * 본래는 post 의 id 만 있어도 불러올 수 있으나
    * post 는 실패했는데 comment 만 불러오는 것도 이상하므로 성공했을 시에만 post comment 를 불러온다.
    *
    * post comment 은 페이징을 사용하므로
    * 페이징 관련 정보를 ViewModel 에 캐싱하기 위해 ViewModel 에서 pagingData 를 꺼내고 cachedIn 을 사용하여
    * 현재 페이지, 현재 페이지 로드 상태등이 ViewModel Scope 내에서 저장되도록 함.
    *  */
    private val postCommentPager = postState
        .onlyAtSuccess()
        .map {
            getCommentForPostUseCase(it.data.id)
                .pagingData
                .cachedIn(platformViewModelScope)
        }

    override val state = combine(
        postState,
        postCommentPager
    ) { postState, commentPager ->
        State(postState, commentPager)
    }.asStateFlow(State(), platformViewModelScope)

    private fun loadPost(postId: Long) {
        getPostByIdUseCase(postId)
            .toLoadState()
            .onEach { postState.emit(it) }
            .launchIn(platformViewModelScope)
    }

    override fun event(e: Event) {
        when (e) {
            Event.Refresh -> {
                val currentPostState = postState.value
                if (currentPostState.isSuccess()) {
                    loadPost(currentPostState.data.id)
                }
            }
            is Event.LoadPost -> {
                loadPost(e.postId)
            }
            is Event.SetPost -> {
                postState.tryEmit(LoadState.success(e.post))
            }
        }
    }
}
