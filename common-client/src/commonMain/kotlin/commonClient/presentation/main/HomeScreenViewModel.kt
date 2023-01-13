package commonClient.presentation.main

import commonClient.data.LoadState
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.domain.usecase.category.GetTrendingCategoriesUseCase
import commonClient.domain.usecase.post.GetHotPostsUseCase
import commonClient.domain.usecase.post.GetPagedPostsUseCase
import commonClient.domain.usecase.post.GetRecentPostsUseCase
import commonClient.domain.usecase.post.GetSelectedPostsUseCase
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import commonClient.utils.toLoadState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

interface HomeScreenEffect {

}

data class HomeScreenState(
    val categories: LoadState<List<Category>> = LoadState.idle(),
    val hotPost: LoadState<List<Post>> = LoadState.idle(),
    val trendingCategories: LoadState<List<Category>> = LoadState.idle(),
    val recentPost: LoadState<List<Post>> = LoadState.idle(),
    val selectedPost: LoadState<List<Post>> = LoadState.idle(),
    val scrappedPosts: LoadState<List<Post>> = LoadState.idle(),
)

class HomeScreenViewModel(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getRecentPostsUseCase: GetRecentPostsUseCase,
    private val getSelectedPostsUseCase: GetSelectedPostsUseCase,
    private val getHotPostsUseCase: GetHotPostsUseCase,
    private val getTrendingCategoriesUseCase: GetTrendingCategoriesUseCase,
) : PlatformViewModel<HomeScreenState, HomeScreenEffect>() {

    override val container: Container<HomeScreenState, HomeScreenEffect> = container(HomeScreenState())

    init {
        intent {
            flow { emit(getCategoriesUseCase()) }
                .toLoadState()
                .onEach {
                    reduce { state.copy(categories = it) }
                    if (it is LoadState.Success) {
                        loadHotPosts(it.data.first(), Duration.WEEK)
                    }
                }
                .launchIn(platformViewModelScope)
        }

        loadTrendingCategories()
        loadRecentPosts()
        loadSelectedPosts()
    }

    fun loadHotPosts(
        category: Category,
        duration: Duration,
    ) = intent {
        flow { emit(getHotPostsUseCase(category, duration)) }
            .toLoadState()
            .onEach { reduce { state.copy(hotPost = it) } }
            .launchIn(platformViewModelScope)
    }

    fun loadTrendingCategories() = intent {
        flow { emit(getTrendingCategoriesUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(trendingCategories = it) } }
            .launchIn(platformViewModelScope)
    }

    fun loadRecentPosts() = intent {
        flow { emit(getRecentPostsUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(recentPost = it) } }
            .launchIn(platformViewModelScope)
    }

    fun loadSelectedPosts() = intent {
        flow { emit(getSelectedPostsUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(selectedPost = it) } }
            .launchIn(platformViewModelScope)
    }

}