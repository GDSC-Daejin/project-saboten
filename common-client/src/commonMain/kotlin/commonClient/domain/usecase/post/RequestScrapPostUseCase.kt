package commonClient.domain.usecase.post

import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class RequestScrapPostUseCase(private val postRepository: PostRepository) {

        suspend operator fun invoke(postId: Long) =
            postRepository.scrapPost(postId)

}