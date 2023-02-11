package commonClient.presentation.main

import com.kuuurt.paging.multiplatform.Pager
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
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

    init {

    }

    fun requestVote(postId: Long, voteId: Long) {
        intent {
            val post = requestVotePostUseCase(postId, VoteSelectRequest(voteId))
            updatePostInList(post)
        }
    }

    fun requestLike(postId: Long) {
        intent {
            val post = requestLikePostUseCase(postId)
            updatePostInList(post)
        }
    }

    fun requestScrap(postId: Long) {
        intent {
            val post = requestScrapPostUseCase(postId)
            updatePostInList(post)
        }
    }

    private suspend fun SimpleSyntax<MoreScreenState, MoreScreenEffect>.updatePostInList(post: Post) {
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


    private val recentPager = createPager<Long, Post>(20, -1) { key, count ->
        val pagingResult = getPagedRecentPostsUseCase(
            PagingRequest()
        )
        PagingResult(
            pagingResult.content,
            currentKey = pagingResult.pageable.offset,
            prevKey = { key },
            nextKey = { pagingResult.pageable.offset + 1 }
        )
    }

    fun setType(type: MoreScreenType) {
        intent {
            reduce {
                val pagingData = when (type) {
                    MoreScreenType.HOT -> {
                        recentPager.pagingData.cachedIn(platformViewModelScope)
                    }

                    MoreScreenType.RECENT -> {
                        recentPager.pagingData.cachedIn(platformViewModelScope)
                    }

                    MoreScreenType.MY_SELECTED -> {
                        recentPager.pagingData.cachedIn(platformViewModelScope)
                    }

                    MoreScreenType.MY_SCRAPPED -> {
                        recentPager.pagingData.cachedIn(platformViewModelScope)
                    }
                }

                state.copy(
                    type = type,
                    items = pagingData
                )
            }
        }
    }

}