package commonClient.presentation.main

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import com.kuuurt.paging.multiplatform.map
import common.model.request.post.VoteSelectRequest
import commonClient.data.LoadState
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Post
import commonClient.domain.entity.user.MyPageCount
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.domain.usecase.post.RequestLikePostUseCase
import commonClient.domain.usecase.post.RequestScrapPostUseCase
import commonClient.domain.usecase.post.RequestVotePostUseCase
import commonClient.domain.usecase.post.paged.GetPagedMyPostsUseCase
import commonClient.domain.usecase.user.GetMyPageCountUseCase
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce


interface ProfileScreenEffect {

}

data class ProfileScreenState(
    val myPageCount: LoadState<MyPageCount> = LoadState.Idle(),
    val myPosts: Flow<PagingData<Post>> = flowOf(),
)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ProfileScreenViewModel(
    private val getMyPageCountUseCase: GetMyPageCountUseCase,
    private val getPagedMyPostsUseCase: GetPagedMyPostsUseCase,
    private val requestScrapPostUseCase: RequestScrapPostUseCase,
    private val requestVotePostUseCase: RequestVotePostUseCase,
    private val requestLikePostUseCase: RequestLikePostUseCase,
) : PlatformViewModel<ProfileScreenState, ProfileScreenEffect>() {

    override val container: Container<ProfileScreenState, ProfileScreenEffect> = container(ProfileScreenState())

    init {
        loadMyPage()
    }

    fun loadMyPage() {
        intent {
            reduce {
                state.copy(myPosts = createMyPostsPager().pagingData)
            }

            flow { emit(getMyPageCountUseCase()) }
                .toLoadState()
                .onEach {
                    reduce { state.copy(myPageCount = it) }
                }
                .launchIn(platformViewModelScope)
        }
    }

    private fun createMyPostsPager() = createPager<Long, Post>(20, -1) { key, _ ->
        val pagingResult = getPagedMyPostsUseCase(PagingRequest(page = key))
        PagingResult(
            pagingResult.data,
            currentKey = key ?: -1,
            prevKey = { null },
            nextKey = { pagingResult.nextKey }
        )
    }

    private val onPostUpdated = { post: Post ->
        intent {
            reduce {
                state.copy(
                    myPosts = state.myPosts.map { pagingData ->
                        pagingData.map { item ->
                            if (item.id == post.id) post
                            else item
                        }
                    }
                )
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