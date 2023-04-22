package commonClient.domain.usecase.post.comment

import commonClient.domain.repository.CommentRepository
import org.koin.core.annotation.Single

@Single
class RequestPostCommentUseCase(private val commentRepository: CommentRepository) {

    suspend operator fun invoke(postId: Long, content: String) = commentRepository.postComment(postId, content)

}