package commonClient.domain.usecase.post.comment

import commonClient.domain.entity.PagingRequest
import commonClient.domain.repository.CommentRepository
import org.koin.core.annotation.Single

@Single
class GetPagedCommentForPostUseCase(
    private val commentRepository: CommentRepository,
) {

    suspend operator fun invoke(
        postId: Long,
        pageRequest: PagingRequest
    ) = commentRepository.getPagedComment(postId, pageRequest)

}