package commonClient.domain.usecase.post.comment

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.CommentRepository

@Singleton
class GetPagedCommentForPostUseCase @Inject constructor(
    private val commentRepository: CommentRepository
) {

//    operator fun invoke(postId: Long) = commentRepository.getCommentsPager(postId)

}