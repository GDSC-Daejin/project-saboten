package commonClient.domain.usecase.post.comment

import commonClient.domain.repository.CommentRepository
import org.koin.core.annotation.Single

@Single
class GetPagedCommentForPostUseCase(
    private val commentRepository: CommentRepository
) {

    operator fun invoke(postId: Long) = commentRepository.getCommentsPager(postId)

}