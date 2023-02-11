package commonClient.presentation.main

import com.kuuurt.paging.multiplatform.Pager
import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import com.kuuurt.paging.multiplatform.map
import common.model.request.post.VoteSelectRequest
import commonClient.data.LoadState
import commonClient.data.map
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.domain.usecase.post.RequestLikePostUseCase
import commonClient.domain.usecase.post.RequestScrapPostUseCase
import commonClient.domain.usecase.post.RequestVotePostUseCase
import commonClient.domain.usecase.post.paged.GetPagedHotPostsUseCase
import commonClient.domain.usecase.post.paged.GetPagedPostsByCategoryUseCase
import commonClient.domain.usecase.post.paged.GetPagedRecentPostsUseCase
import commonClient.domain.usecase.post.paged.GetPagedScrappedPostsUseCase
import commonClient.domain.usecase.post.paged.GetPagedVotedPostsUseCase
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
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce


interface CategoryScreenEffect {

}

data class CategoryScreenState(
    val categories: LoadState<List<Category>> = LoadState.idle(),
    val selectedCategoryId: Long? = null,
    val items: Flow<PagingData<Post>> = flowOf(),
)

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class CategoryScreenViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getPagedPostsByCategoryUseCase: GetPagedPostsByCategoryUseCase,
    postActionsDelegate: PostActionsDelegate,
) : PlatformViewModel<CategoryScreenState, CategoryScreenEffect>(), PostActionsDelegate by postActionsDelegate {

    override val container: Container<CategoryScreenState, CategoryScreenEffect> = container(CategoryScreenState())

    init {
        containerHost = this
        intent {
            flow { emit(getCategoriesUseCase()) }
                .toLoadState()
                .onEach { categories ->
                    // 전체 보여주기
                    val pager = createPagerByCategoryId(null)
                    reduce {
                        state.copy(
                            categories = categories.map { listOf(Category(-1, "전체", "", "", "")) + it },
                            items = pager.pagingData.cachedIn(platformViewModelScope)
                        )
                    }
                }.launchIn(platformViewModelScope)
        }
    }

    fun selectCategory(categoryId: Long?) {
        intent {
            val pager = createPagerByCategoryId(categoryId)
            reduce {
                state.copy(
                    selectedCategoryId = categoryId,
                    items = pager.pagingData.cachedIn(platformViewModelScope)
                )
            }
        }
    }

    private fun createPagerByCategoryId(categoryId: Long?) = createPager<Long, Post>(20, -1) { key, _ ->
        val pagingResult = getPagedPostsByCategoryUseCase(categoryId, PagingRequest(offset = key))
        PagingResult(
            pagingResult.content,
            currentKey = pagingResult.pageable.offset,
            prevKey = { key },
            nextKey = { pagingResult.pageable.offset + 1 }
        )
    }

    override suspend fun onPostUpdated(post: Post) {
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

}