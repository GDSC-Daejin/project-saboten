package commonClient.presentation.main

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import com.kuuurt.paging.multiplatform.map
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.post.AddRecentSearchTextsUseCase
import commonClient.domain.usecase.post.ClearRecentSearchTextsUseCase
import commonClient.domain.usecase.post.GetRecentSearchTextsUseCase
import commonClient.domain.usecase.post.RemoveRecentSearchTextsUseCase
import commonClient.domain.usecase.post.paged.GetPagedSearchPostsUseCase
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import commonClient.presentation.post.PostActionsDelegate
import commonClient.utils.createPager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

data class SearchScreenState(
    val searchHistories: List<String> = emptyList(),
    val lastSearchedQuery: String? = null,
    val totalCount: Long? = null,
    val items: Flow<PagingData<Post>> = flowOf(),
)

interface SearchScreenEffect {

}

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchScreenViewModel(
    private val getPagedSearchPostsUseCase: GetPagedSearchPostsUseCase,
    private val addRecentSearchTextsUseCase: AddRecentSearchTextsUseCase,
    private val removeRecentSearchTextsUseCase: RemoveRecentSearchTextsUseCase,
    private val clearRecentSearchTextsUseCase: ClearRecentSearchTextsUseCase,
    private val getRecentSearchTextsUseCase: GetRecentSearchTextsUseCase,
    postActionsDelegate: PostActionsDelegate,
) : PlatformViewModel<SearchScreenState, SearchScreenEffect>(), PostActionsDelegate by postActionsDelegate {

    override val container: Container<SearchScreenState, SearchScreenEffect> = container(SearchScreenState())

    init {
        containerHost = this
        intent {
            getRecentSearchTextsUseCase()
                .onEach {
                    reduce { state.copy(searchHistories = it) }
                }
                .launchIn(platformViewModelScope)
        }
    }

    fun removeSearchHistory(history: String) = intent {
        removeRecentSearchTextsUseCase(history)
    }

    fun clearSearchHistory() = intent {
        clearRecentSearchTextsUseCase()
    }

    fun search(query: String) = intent {
        addRecentSearchTextsUseCase(query)

        reduce { state.copy(lastSearchedQuery = null, totalCount = null) }

        reduce {

            val searchedPager = createPager<Long, Post>(20, -1) { key, _ ->
                val pagingResult = getPagedSearchPostsUseCase(query, PagingRequest(page = key))

                intent {
                    reduce {
                        state.copy(
                            lastSearchedQuery = query,
                            totalCount = pagingResult.count ?: 0
                        )
                    }
                }

                PagingResult(
                    pagingResult.data,
                    currentKey = key ?: -1,
                    prevKey = { null },
                    nextKey = { pagingResult.nextKey }
                )
            }

            state.copy(
                items = searchedPager.pagingData.cachedIn(platformViewModelScope)
            )
        }
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