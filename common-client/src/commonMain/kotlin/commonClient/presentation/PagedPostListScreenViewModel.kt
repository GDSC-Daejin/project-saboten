package commonClient.presentation

import com.kuuurt.paging.multiplatform.PagingData
import com.kuuurt.paging.multiplatform.PagingResult
import com.kuuurt.paging.multiplatform.helpers.cachedIn
import commonClient.di.HiltViewModel
import commonClient.di.Inject
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.post.GetPagedPostByCategoryIdUseCase
import commonClient.presentation.PagedPostListScreenViewModelDelegate.*
import commonClient.utils.createPager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface PagedPostListScreenViewModelDelegate :
    UnidirectionalViewModelDelegate<State, Effect, Event> {

    data class State(
        val postPage: Flow<PagingData<Post>> = flowOf()
    )

    sealed class Effect {

    }

    sealed class Event {
        class LoadPostsWithCategoryId(val categoryId: Long) : Event()
    }

}

@HiltViewModel
class PagedPostListScreenViewModel @Inject constructor(
    getPagedPostByCategoryIdUseCase: GetPagedPostByCategoryIdUseCase
) : PlatformViewModel(), PagedPostListScreenViewModelDelegate {

    private val effectChannel = Channel<Effect>(Channel.UNLIMITED)
    override val effect: Flow<Effect> = effectChannel.receiveAsFlow()

    private val selectedCategoryId = MutableStateFlow<Long?>(null)

    private val postPager = selectedCategoryId
        .filterNotNull()
        .map { categoryId ->
            createPager<Long, Post>(
                10,
                null
            ) { currentKey, _ ->
                val response = getPagedPostByCategoryIdUseCase(categoryId, currentKey)
                PagingResult(
                    response.data,
                    currentKey,
                    { null },
                    { response.nextKey }
                )
            }
        }

    override val state = postPager.map {
        State(it.pagingData.cachedIn(platformViewModelScope))
    }.asStateFlow(State(), platformViewModelScope)

    override fun event(e: Event) {
        when (e) {
            is Event.LoadPostsWithCategoryId -> {
                platformViewModelScope.launch {
                    selectedCategoryId.emit(e.categoryId)
                }
            }
        }
    }

}