package commonClient.domain.usecase.post.paged

import commonClient.domain.entity.PagingRequest
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetPagedRecentPostsUseCase(
    private val postRepository: PostRepository,
) {

    suspend operator fun invoke(pagingRequest: PagingRequest) = postRepository.getRecentPosts(pagingRequest)

}
