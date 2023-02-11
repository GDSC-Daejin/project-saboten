package commonClient.presentation.main

import common.model.request.post.VoteSelectRequest
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.post.GetPostByIdUseCase
import commonClient.domain.usecase.post.RequestLikePostUseCase
import commonClient.domain.usecase.post.RequestScrapPostUseCase
import commonClient.domain.usecase.post.RequestVotePostUseCase
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import commonClient.utils.toLoadState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

interface DetailPostScreenEffect {

}

data class DetailPostScreenState(
    val id: Long = 1717,
    val selectedVoteId: Long? = null,
    val post: Post? = null,
    val selectedCategoryId: Long? = null,
)

class DetailPostScreenViewModel(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val requestLikePostUseCase: RequestLikePostUseCase,
    private val requestScrapPostUseCase: RequestScrapPostUseCase,
    private val requestVotePostUseCase: RequestVotePostUseCase,
    private val postActionsDelegate: PostActionsDelegate,
) : PlatformViewModel<DetailPostScreenState, DetailPostScreenEffect>() {

    override val container: Container<DetailPostScreenState, DetailPostScreenEffect> =
        container(DetailPostScreenState())

    init {
        postActionsDelegate.containerHost = this
        intent {
            flow { emit(getPostByIdUseCase(state.id)) }
                .toLoadState()
                .onEach { post ->
                    reduce {
                        state.copy(
                            post = post.getDataOrNull(),
                            selectedCategoryId = post.getDataOrNull()?.selectedVote
                        )
                    }
                }.launchIn(platformViewModelScope)
        }

    }

    fun selectVote(voteId: Long) = intent {
        flow { emit(requestVotePostUseCase(state.id, VoteSelectRequest(voteId))) }
            .toLoadState()
            .onEach { post ->
                reduce {
                    state.copy(
                        post = post.getDataOrNull(),
                        selectedCategoryId = post.getDataOrNull()?.selectedVote
                    )
                }
            }.launchIn(platformViewModelScope)
    }

    fun likePost() = intent {
        flow { emit(requestLikePostUseCase(state.id)) }
            .toLoadState()
            .onEach { post ->
                reduce {
                    state.copy(
                        post = post.getDataOrNull(),
                        selectedCategoryId = post.getDataOrNull()?.selectedVote
                    )
                }
            }.launchIn(platformViewModelScope)
    }

    fun scrapPost() =
        intent {
            flow { emit(requestScrapPostUseCase(state.id)) }
                .toLoadState()
                .onEach { post ->
                    reduce {
                        state.copy(
                            post = post.getDataOrNull(),
                            selectedCategoryId = post.getDataOrNull()?.selectedVote
                        )
                    }
                }.launchIn(platformViewModelScope)
        }


}