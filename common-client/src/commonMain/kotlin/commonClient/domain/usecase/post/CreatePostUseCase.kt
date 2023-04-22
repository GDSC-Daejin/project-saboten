package commonClient.domain.usecase.post

import common.model.request.post.create.PostCreateRequest
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class CreatePostUseCase(private val postRepository: PostRepository) {
    suspend operator fun invoke(postCreateRequest: PostCreateRequest) = postRepository.createPost(postCreateRequest)
}