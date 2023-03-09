package commonClient.presentation.post

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import commonClient.data.LoadState
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Comment
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.post.GetPostByIdUseCase
import commonClient.domain.usecase.post.comment.GetPagedCommentForPostUseCase
import commonClient.domain.usecase.post.comment.RequestPostCommentUseCase
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import commonClient.utils.createPager
import commonClient.utils.toLoadState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

interface DetailPostScreenEffect {
    object CommentBlank : DetailPostScreenEffect
    object CommentPosted : DetailPostScreenEffect
}

data class DetailPostScreenState(
    val post: LoadState<Post> = LoadState.idle(),
    val comments: Flow<PagingData<Comment>> = flowOf(),
)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class DetailPostScreenViewModel(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getPagedCommentForPostUseCase: GetPagedCommentForPostUseCase,
    private val requestPostCommentUseCase: RequestPostCommentUseCase,
    private val postActionsDelegate: PostActionsDelegate,
) : PlatformViewModel<DetailPostScreenState, DetailPostScreenEffect>(), PostActionsDelegate by postActionsDelegate {

    override val container: Container<DetailPostScreenState, DetailPostScreenEffect> =
        container(DetailPostScreenState())

    init {
        containerHost = this
    }

    fun postComment(postId: Long, content: String) = intent {
        if (content.isBlank()) {
            postSideEffect(DetailPostScreenEffect.CommentBlank)
            return@intent
        }
        flow { emit(requestPostCommentUseCase(postId, content)) }
            .toLoadState()
            .onEach { postSideEffect(DetailPostScreenEffect.CommentPosted) }
            .launchIn(platformViewModelScope)
    }

    fun loadPost(id: Long) = intent {
        flow { emit(getPostByIdUseCase(id)) }
            .toLoadState()
            .onEach { post ->

                val flow = createPager<Long, Comment>(20, -1) { key, _ ->
                    val pagingResult = getPagedCommentForPostUseCase(id, PagingRequest(page = key))
                    PagingResult(
                        pagingResult.data,
                        currentKey = key ?: -1,
                        prevKey = { null },
                        nextKey = { pagingResult.nextKey }
                    )
                }.pagingData.cachedIn(platformViewModelScope)

                reduce {
                    state.copy(post = post, comments = flow)
                }
            }.launchIn(platformViewModelScope)
    }

}