package commonClient.domain.usecase.post

import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class DeletePostUseCase constructor(private val postRepository: PostRepository) {
    suspend operator fun invoke(postId: Long) = postRepository.deletePost(postId)
}