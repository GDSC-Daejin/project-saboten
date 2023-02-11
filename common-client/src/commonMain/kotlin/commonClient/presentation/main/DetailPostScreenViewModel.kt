package commonClient.presentation.main

import common.model.request.post.VoteSelectRequest
import commonClient.data.LoadState
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
    val post: LoadState<Post> = LoadState.idle(),
)

class DetailPostScreenViewModel(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val postActionsDelegate: PostActionsDelegate,
) : PlatformViewModel<DetailPostScreenState, DetailPostScreenEffect>(), PostActionsDelegate by postActionsDelegate {

    override val container: Container<DetailPostScreenState, DetailPostScreenEffect> =
        container(DetailPostScreenState())

    init {
        containerHost = this
    }

    fun loadPost(id : Long) = intent {
        flow { emit(getPostByIdUseCase(id)) }
            .toLoadState()
            .onEach { post ->
                reduce {
                    state.copy(post = post)
                }
            }.launchIn(platformViewModelScope)
    }

}