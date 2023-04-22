package commonClient.presentation.post

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import common.model.request.post.VoteSelectRequest
import commonClient.data.LoadState
import commonClient.data.isFailed
import commonClient.data.isLoading
import commonClient.data.isSuccess
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Comment
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.post.GetPostByIdUseCase
import commonClient.domain.usecase.post.RequestLikePostUseCase
import commonClient.domain.usecase.post.RequestScrapPostUseCase
import commonClient.domain.usecase.post.RequestVotePostUseCase
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
    object CommentPosting : DetailPostScreenEffect
    object CommentPosted : DetailPostScreenEffect

    object CommentPostFailed : DetailPostScreenEffect
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
    private val requestScrapPostUseCase: RequestScrapPostUseCase,
    private val requestVotePostUseCase: RequestVotePostUseCase,
    private val requestLikePostUseCase: RequestLikePostUseCase,
) : PlatformViewModel<DetailPostScreenState, DetailPostScreenEffect>() {

    override val container: Container<DetailPostScreenState, DetailPostScreenEffect> =
        container(DetailPostScreenState())

    fun postComment(postId: Long, content: String) = intent {
        if (content.isBlank()) {
            postSideEffect(DetailPostScreenEffect.CommentBlank)
            return@intent
        }

        flow { emit(requestPostCommentUseCase(postId, content)) }
            .toLoadState()
            .onEach {
                if (it.isSuccess()) {
                    refreshComment()
                    postSideEffect(DetailPostScreenEffect.CommentPosted)
                }
                if (it.isLoading()) {
                    postSideEffect(DetailPostScreenEffect.CommentPosting)
                }

                if (it.isFailed()) {
                    postSideEffect(DetailPostScreenEffect.CommentPostFailed)
                }
            }
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

    fun refreshComment() {
        intent {
            reduce {
                val flow = createPager<Long, Comment>(20, -1) { key, _ ->
                    val pagingResult = getPagedCommentForPostUseCase(state.post.getDataOrNull()?.id ?: -1, PagingRequest(page = key))
                    PagingResult(
                        pagingResult.data,
                        currentKey = key ?: -1,
                        prevKey = { null },
                        nextKey = { pagingResult.nextKey }
                    )
                }.pagingData.cachedIn(platformViewModelScope)

                state.copy(comments = flow)
            }
        }
    }

    private val onPostUpdated = { post: Post ->
        intent {
            reduce {
                state.copy(post = LoadState.success(post))
            }
        }
    }

    fun requestVote(postId: Long, voteId: Long) {
        intent {
            val post = requestVotePostUseCase(postId, VoteSelectRequest(voteId))
            onPostUpdated(post)
        }
    }

    fun requestLike(postId: Long) {
        intent {
            val post = requestLikePostUseCase(postId)
            onPostUpdated(post)
        }
    }

    fun requestScrap(postId: Long) {
        intent {
            val post = requestScrapPostUseCase(postId)
            onPostUpdated(post)
        }
    }

}