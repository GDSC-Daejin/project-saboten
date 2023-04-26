package commonClient.presentation.main

import common.model.request.post.VoteSelectRequest
import commonClient.data.LoadState
import commonClient.domain.entity.banner.Banner
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.CategoryType
import commonClient.domain.entity.post.HotPostSortState
import commonClient.domain.entity.post.PeriodType
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.banner.GetBannerUseCase
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.domain.usecase.category.GetTrendingCategoriesUseCase
import commonClient.domain.usecase.post.GetHotPostsUseCase
import commonClient.domain.usecase.post.GetRecentPostsUseCase
import commonClient.domain.usecase.post.GetScrappedPostsUseCase
import commonClient.domain.usecase.post.GetSelectedPostsUseCase
import commonClient.domain.usecase.post.RequestLikePostUseCase
import commonClient.domain.usecase.post.RequestScrapPostUseCase
import commonClient.domain.usecase.post.RequestVotePostUseCase
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import commonClient.utils.toLoadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

interface HomeScreenEffect {

}

data class HomeScreenState(
    val banners: LoadState<List<Banner>> = LoadState.idle(),
    val categories: LoadState<List<Category>> = LoadState.idle(),
    val hotPost: LoadState<List<Post>> = LoadState.idle(),
    val trendingCategories: LoadState<List<Category>> = LoadState.idle(),
    val recentPost: LoadState<List<Post>> = LoadState.idle(),
    val selectedPost: LoadState<List<Post>> = LoadState.idle(),
    val scrappedPosts: LoadState<List<Post>> = LoadState.idle(),
    val hotPostSortState: LoadState<HotPostSortState> = LoadState.Success(HotPostSortState()),
)

class HomeScreenViewModel(
    private val getBannerUseCase: GetBannerUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getRecentPostsUseCase: GetRecentPostsUseCase,
    private val getSelectedPostsUseCase: GetSelectedPostsUseCase,
    private val getScrappedPostsUseCase: GetScrappedPostsUseCase,
    private val getHotPostsUseCase: GetHotPostsUseCase,
    private val getTrendingCategoriesUseCase: GetTrendingCategoriesUseCase,
    private val requestScrapPostUseCase: RequestScrapPostUseCase,
    private val requestVotePostUseCase: RequestVotePostUseCase,
    private val requestLikePostUseCase: RequestLikePostUseCase,
) : PlatformViewModel<HomeScreenState, HomeScreenEffect>() {

    override val container: Container<HomeScreenState, HomeScreenEffect> =
        container(HomeScreenState())

    fun loadPage() {
        loadCategories()
        loadBanners()
        loadTrendingCategories()
        loadRecentPosts()
        loadSelectedPosts()
        loadScrappedPosts()
    }

    fun setHotPostSortState(hotPostSortState: HotPostSortState) = intent {
        reduce { state.copy(hotPostSortState = LoadState.success(hotPostSortState)) }
    }

    private fun loadCategories() = intent {
        flow { emit(getCategoriesUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(categories = it) } }
            .launchIn(platformViewModelScope)
    }

    private fun loadBanners() = intent {
        flow { emit(getBannerUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(banners = it) } }
            .launchIn(platformViewModelScope)
    }

    fun loadHotPosts() = intent {
        flow {
            val hotPostSortState =
                requireNotNull(container.stateFlow.value.hotPostSortState.getDataOrNull())
            val category = (hotPostSortState.categoryState.type as CategoryType)
            val duration = (hotPostSortState.periodState.type as PeriodType).toDuration()
            emit(getHotPostsUseCase(category.id, duration))
        }
            .toLoadState()
            .onEach { reduce { state.copy(hotPost = it) } }
            .launchIn(platformViewModelScope)
    }

    private fun loadTrendingCategories() = intent {
        flow { emit(getTrendingCategoriesUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(trendingCategories = it) } }
            .launchIn(platformViewModelScope)
    }

    private fun loadRecentPosts() = intent {
        flow { emit(getRecentPostsUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(recentPost = it) } }
            .launchIn(platformViewModelScope)
    }

    private fun loadSelectedPosts() = intent {
        flow { emit(getSelectedPostsUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(selectedPost = it) } }
            .launchIn(platformViewModelScope)
    }

    private fun loadScrappedPosts() = intent {
        flow { emit(getScrappedPostsUseCase()) }
            .toLoadState()
            .onEach { reduce { state.copy(scrappedPosts = it) } }
            .launchIn(platformViewModelScope)
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