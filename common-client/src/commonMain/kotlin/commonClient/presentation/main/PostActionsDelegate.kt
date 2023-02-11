package commonClient.presentation.main

import com.kuuurt.paging.multiplatform.map
import common.model.request.post.VoteSelectRequest
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.post.RequestLikePostUseCase
import commonClient.domain.usecase.post.RequestScrapPostUseCase
import commonClient.domain.usecase.post.RequestVotePostUseCase
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce


interface PostActionsDelegate {

    var containerHost: ContainerHost<*, *>?

    fun requestVote(postId: Long, voteId: Long)

    fun requestLike(postId: Long)

    fun requestScrap(postId: Long)

    suspend fun onPostUpdated(post: Post)

}


@Single(binds = [PostActionsDelegate::class])
open class PostActionsDelegateImp(
    private val requestScrapPostUseCase: RequestScrapPostUseCase,
    private val requestVotePostUseCase: RequestVotePostUseCase,
    private val requestLikePostUseCase: RequestLikePostUseCase,
) : PostActionsDelegate {

    override var containerHost: ContainerHost<*, *>? = null

    override fun requestVote(postId: Long, voteId: Long) {
       containerHost?.intent {
            val post = requestVotePostUseCase(postId, VoteSelectRequest(voteId))
            onPostUpdated(post)
        }
    }

    override fun requestLike(postId: Long) {
       containerHost?.intent {
            val post = requestLikePostUseCase(postId)
            onPostUpdated(post)
        }
    }

    override fun requestScrap(postId: Long) {
       containerHost?.intent {
            val post = requestScrapPostUseCase(postId)
            onPostUpdated(post)
        }
    }

    override suspend fun onPostUpdated(post: Post) {
    }
}