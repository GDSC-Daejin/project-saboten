package commonClient.presentation.main

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import com.kuuurt.paging.multiplatform.map
import common.model.request.post.VoteSelectRequest
import commonClient.domain.entity.PagingRequest
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
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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
                    MoreScreenType.HOT -> TODO("Not yet implemented")

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

    private val onPostUpdated = { post : Post ->
        intent {
            reduce {
                state.copy(
                    items = state.items.map { pagingData ->
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