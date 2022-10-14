package commonClient.domain.usecase.post

import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetPostByIdUseCase(
    private val postRepository: PostRepository
) {

    operator fun invoke(id: Long) = postRepository.postsById(id)

}