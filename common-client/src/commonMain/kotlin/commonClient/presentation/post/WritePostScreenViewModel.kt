package commonClient.presentation.post

import common.model.VoteColorsResponse
import common.model.request.post.create.PostCreateRequest
import common.model.request.post.create.VoteCreateRequest
import commonClient.data.LoadState
import commonClient.data.isFailed
import commonClient.data.isLoading
import commonClient.data.isSuccess
import commonClient.data.map
import commonClient.domain.entity.post.Category
import commonClient.domain.usecase.category.GetCategoriesUseCase
import commonClient.domain.usecase.post.CreatePostUseCase
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import commonClient.utils.toLoadState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

interface WritePostScreenEffect {

    object CreatePosing : WritePostScreenEffect
    object CreatePosted : WritePostScreenEffect
    object CreatePostFailed : WritePostScreenEffect
    object UnSelectedCategory : WritePostScreenEffect

}

data class WritePostScreenState(
    val createPost: LoadState<Unit> = LoadState.idle(),
    val categories: LoadState<List<Category>> = LoadState.idle(),
    val selectedCategoryIds: List<Long> = emptyList(),
)

class WritePostScreenViewModel(
    private val createPostUseCase: CreatePostUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
) : PlatformViewModel<WritePostScreenState, WritePostScreenEffect>() {

    override val container: Container<WritePostScreenState, WritePostScreenEffect> =
        container(WritePostScreenState())

    init {
        intent {
            flow { emit(getCategoriesUseCase()) }
                .toLoadState()
                .onEach { categories ->
                    reduce {
                        state.copy(
                            categories = categories.map {
                                it.filter { category ->
                                    category.id != 10L
                                }.sortedBy { category -> category.id }
                            },
                        )
                    }
                }.launchIn(platformViewModelScope)
        }

    }

    fun selectCategory(categoryId: Long) {
        intent {
            reduce {
                state.copy(
                    selectedCategoryIds =
                    if (categoryId in state.selectedCategoryIds) {
                        state.selectedCategoryIds.filter { it != categoryId }
                    } else {
                        state.selectedCategoryIds + categoryId
                    }
                )
            }
        }
    }

    fun createPost(
        titleText: String,
        firstTopicText: String,
        secondTopicText: String
    ) = intent {

        if (state.selectedCategoryIds.isNotEmpty()) {
            flow {
                emit(
                    createPostUseCase(
                        postCreateRequest =
                        PostCreateRequest(
                            text = titleText,
                            voteTopics = listOf(
                                VoteCreateRequest(firstTopicText, VoteColorsResponse.GREEN),
                                VoteCreateRequest(secondTopicText, VoteColorsResponse.GREEN),
                            ),
                            categoryIds = state.selectedCategoryIds
                        )
                    )
                )
            }
                .toLoadState()
                .onEach {
                    if (it.isSuccess()) {
                        postSideEffect(WritePostScreenEffect.CreatePosted)
                    }
                    if (it.isLoading()) {
                        postSideEffect(WritePostScreenEffect.CreatePosing)
                    }
                    if (it.isFailed()) {
                        postSideEffect(WritePostScreenEffect.CreatePostFailed)
                    }
                }
                .launchIn(platformViewModelScope)
        } else {
            postSideEffect(WritePostScreenEffect.UnSelectedCategory)
        }

    }

}