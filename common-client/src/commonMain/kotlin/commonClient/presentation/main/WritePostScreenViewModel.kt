package commonClient.presentation.main

import common.model.VoteColorsResponse
import common.model.request.post.create.PostCreateRequest
import common.model.request.post.create.VoteCreateRequest
import commonClient.data.LoadState
import commonClient.data.map
import commonClient.domain.entity.banner.Banner
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
import org.orbitmvi.orbit.syntax.simple.reduce

interface WritePostScreenEffect {

}

data class WritePostScreenState(
    val createPost: LoadState<List<Banner>> = LoadState.idle(),
    val categories: LoadState<List<Category>> = LoadState.idle(),
    val selectedCategoryId: Long? = null,
)

class WritePostScreenViewModel(
    private val createPostUseCase: CreatePostUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val postActionsDelegate: PostActionsDelegate,
) : PlatformViewModel<WritePostScreenState, WritePostScreenEffect>() {

    override val container: Container<WritePostScreenState, WritePostScreenEffect> =
        container(WritePostScreenState())

    init {
        postActionsDelegate.containerHost = this
        intent {
            flow { emit(getCategoriesUseCase()) }
                .toLoadState()
                .onEach { categories ->
                    reduce {
                        state.copy(
                            categories = categories.map {
                                listOf(
                                    Category(
                                        -1,
                                        "전체",
                                        "",
                                        "",
                                        ""
                                    )
                                ) + it
                            },
                        )
                    }
                }.launchIn(platformViewModelScope)
        }

    }

    fun selectCategory(categoryId: Long?) {
        intent {
            reduce {
                state.copy(
                    selectedCategoryId = categoryId
                )
            }
        }
    }

    fun createPost(
        titleText: String,
        firstTopicText: String,
        secondTopicText: String
    ) = intent {
        createPostUseCase(
            postCreateRequest =
            PostCreateRequest(
                text = titleText,
                voteTopics = listOf(
                    VoteCreateRequest(firstTopicText, VoteColorsResponse.GREEN),
                    VoteCreateRequest(secondTopicText, VoteColorsResponse.GREEN),
                ),
                categoryIds = listOf(state.selectedCategoryId ?: -1)
            )
        )
    }

}