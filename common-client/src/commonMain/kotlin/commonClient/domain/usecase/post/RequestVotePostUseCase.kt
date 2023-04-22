package commonClient.domain.usecase.post

import common.model.request.post.VoteSelectRequest
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class RequestVotePostUseCase(private val postRepository: PostRepository) {

        suspend operator fun invoke(postId: Long, voteSelectRequest: VoteSelectRequest) =
            postRepository.votePost(postId, voteSelectRequest)

}