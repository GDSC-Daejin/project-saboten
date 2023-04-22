package commonClient.presentation.main

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.map
import common.model.request.post.VoteSelectRequest
import commonClient.data.LoadState
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Post
import commonClient.domain.entity.user.MyPageCount
import commonClient.domain.usecase.post.RequestLikePostUseCase
import commonClient.domain.usecase.post.RequestScrapPostUseCase
import commonClient.domain.usecase.post.RequestVotePostUseCase
import commonClient.domain.usecase.post.paged.GetPagedMyPostsUseCase
import commonClient.domain.usecase.post.paged.GetPagedScrappedPostsUseCase
import commonClient.domain.usecase.post.paged.GetPagedVotedPostsUseCase
import commonClient.domain.usecase.user.GetMyPageCountUseCase
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import commonClient.utils.createPager
import commonClient.utils.toLoadState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val selectedType: ProfileType = ProfileType.MY_POSTS,
    val myPageCount: LoadState<MyPageCount> = LoadState.Idle(),
    val myPosts: Flow<PagingData<Post>> = flowOf(),
) {
    enum class ProfileType {
        MY_POSTS,
        SCRAPPED_POSTS,
        VOTED_POSTS,
    }
}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class ProfileScreenViewModel(
    private val getMyPageCountUseCase: GetMyPageCountUseCase,
    private val getPagedMyPostsUseCase: GetPagedMyPostsUseCase,
    private val getPagedScrappedPostsUseCase: GetPagedScrappedPostsUseCase,
    private val getPagedVotedPostsUseCase: GetPagedVotedPostsUseCase,
    private val requestScrapPostUseCase: RequestScrapPostUseCase,
    private val requestVotePostUseCase: RequestVotePostUseCase,
    private val requestLikePostUseCase: RequestLikePostUseCase,
) : PlatformViewModel<ProfileScreenState, ProfileScreenEffect>() {

    override val container: Container<ProfileScreenState, ProfileScreenEffect> = container(ProfileScreenState())

    init {
        load()
    }

    fun load(type: ProfileScreenState.ProfileType = ProfileScreenState.ProfileType.MY_POSTS) {
        intent {
            reduce { state.copy(selectedType = type) }
            loadMyPageCount()
            loadMyPagePosts(type)
        }
    }

    private fun loadMyPageCount() {
        intent {
            flow { emit(getMyPageCountUseCase()) }
                .toLoadState()
                .onEach { reduce { state.copy(myPageCount = it) } }
                .launchIn(platformViewModelScope)
        }
    }

    private fun loadMyPagePosts(type: ProfileScreenState.ProfileType) {
        intent {
            reduce {
                state.copy(
                    myPosts = when (type) {
                        ProfileScreenState.ProfileType.MY_POSTS -> createMyPostsPager().pagingData
                        ProfileScreenState.ProfileType.SCRAPPED_POSTS -> createScrappedPostsPager().pagingData
                        ProfileScreenState.ProfileType.VOTED_POSTS -> createVotedPostsPager().pagingData
                    }
                )
            }
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

    private fun createScrappedPostsPager() = createPager<Long, Post>(20, -1) { key, _ ->
        val pagingResult = getPagedScrappedPostsUseCase(PagingRequest(page = key))
        PagingResult(
            pagingResult.data,
            currentKey = key ?: -1,
            prevKey = { null },
            nextKey = { pagingResult.nextKey }
        )
    }

    private fun createVotedPostsPager() = createPager<Long, Post>(20, -1) { key, _ ->
        val pagingResult = getPagedVotedPostsUseCase(PagingRequest(page = key))
        PagingResult(
            pagingResult.data,
            currentKey = key ?: -1,
            prevKey = { null },
            nextKey = { pagingResult.nextKey }
        )
    }

    private val _updatedPostCache = MutableStateFlow(mutableListOf<Post>())
    val updatedPostCache: StateFlow<List<Post>> = _updatedPostCache

    private fun updatePost(post: Post) {
        intent {
            val updatedPostCache = _updatedPostCache.value
            updatedPostCache.removeAll { it.id == post.id }
            _updatedPostCache.emit((updatedPostCache + post).toMutableList())
        }
    }

    fun requestVote(postId: Long, voteId: Long) {
        intent {
            val post = requestVotePostUseCase(postId, VoteSelectRequest(voteId))
            updatePost(post)
        }
    }

    fun requestLike(postId: Long) {
        intent {
            val post = requestLikePostUseCase(postId)
            updatePost(post)
        }
    }

    fun requestScrap(postId: Long) {
        intent {
            val post = requestScrapPostUseCase(postId)
            updatePost(post)
        }
    }

}