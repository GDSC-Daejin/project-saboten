package commonClient.presentation.main

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import common.model.request.post.VoteSelectRequest
import commonClient.data.LoadState
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.CategoryType
import commonClient.domain.entity.post.HotPostSortState
import commonClient.domain.entity.post.PeriodType
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.post.RequestLikePostUseCase
import commonClient.domain.usecase.post.RequestScrapPostUseCase
import commonClient.domain.usecase.post.RequestVotePostUseCase
import commonClient.domain.usecase.post.paged.GetPagedHotPostsUseCase
import commonClient.domain.usecase.post.paged.GetPagedRecentPostsUseCase
import commonClient.domain.usecase.post.paged.GetPagedScrappedPostsUseCase
import commonClient.domain.usecase.post.paged.GetPagedVotedPostsUseCase
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import commonClient.utils.createPager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce


interface MoreScreenEffect {

}

enum class MoreScreenType {
    HOT,
    RECENT,
    MY_SELECTED,
    MY_SCRAPPED
}

data class MoreScreenState(
    val type: MoreScreenType = MoreScreenType.HOT,
    val items: Flow<PagingData<Post>> = flowOf(),
    val hotPostSortState: LoadState<HotPostSortState> = LoadState.Success(HotPostSortState()),
)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MoreScreenViewModel(
    private val getPagedHotPostsUseCase: GetPagedHotPostsUseCase,
    private val getPagedRecentPostsUseCase: GetPagedRecentPostsUseCase,
    private val getPagedVotedPostsUseCase: GetPagedVotedPostsUseCase,
    private val getPagedScrappedPostsUseCase: GetPagedScrappedPostsUseCase,
    private val requestScrapPostUseCase: RequestScrapPostUseCase,
    private val requestVotePostUseCase: RequestVotePostUseCase,
    private val requestLikePostUseCase: RequestLikePostUseCase,
) : PlatformViewModel<MoreScreenState, MoreScreenEffect>() {

    override val container: Container<MoreScreenState, MoreScreenEffect> = container(MoreScreenState())

    fun setHotPostSortState(hotPostSortState: HotPostSortState) = intent {
        reduce { state.copy(hotPostSortState = LoadState.success(hotPostSortState)) }
    }

    private val hotPager = createPager<Long, Post>(20, -1) { key, _ ->

        val hotPostSortState =
            requireNotNull(container.stateFlow.value.hotPostSortState.getDataOrNull())
        val category = (hotPostSortState.categoryState.type as CategoryType)
        val duration = (hotPostSortState.periodState.type as PeriodType).toDuration()

        val pagingResult = getPagedHotPostsUseCase(category.id, duration, PagingRequest(page = key))
        PagingResult(
            pagingResult.data,
            currentKey = key ?: -1,
            prevKey = { null },
            nextKey = { pagingResult.nextKey }
        )
    }

    private val recentPager = createPager<Long, Post>(20, -1) { key, _ ->
        val pagingResult = getPagedRecentPostsUseCase(PagingRequest(page = key))
        PagingResult(
            pagingResult.data,
            currentKey = key ?: -1,
            prevKey = { null },
            nextKey = { pagingResult.nextKey }
        )
    }

    private val selectedPager = createPager<Long, Post>(20, -1) { key, _ ->
        val pagingResult = getPagedVotedPostsUseCase(PagingRequest(page = key))
        PagingResult(
            pagingResult.data,
            currentKey = key ?: -1,
            prevKey = { null },
            nextKey = { pagingResult.nextKey }
        )
    }

    private val scrappedPager = createPager<Long, Post>(20, -1) { key, _ ->
        val pagingResult = getPagedScrappedPostsUseCase(PagingRequest(page = key))
        PagingResult(
            pagingResult.data,
            currentKey = key ?: -1,
            prevKey = { null },
            nextKey = { pagingResult.nextKey }
        )
    }

    fun setType(type: MoreScreenType) {
        intent {
            reduce {
                val pagingData = when (type) {
                    MoreScreenType.HOT -> hotPager.pagingData.cachedIn(platformViewModelScope)

                    MoreScreenType.RECENT -> recentPager.pagingData.cachedIn(platformViewModelScope)

                    MoreScreenType.MY_SELECTED -> selectedPager.pagingData.cachedIn(platformViewModelScope)

                    MoreScreenType.MY_SCRAPPED -> scrappedPager.pagingData.cachedIn(platformViewModelScope)
                }

                state.copy(
                    type = type,
                    items = pagingData
                )
            }
        }
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