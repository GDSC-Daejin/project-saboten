package commonClient.domain.usecase.post.paged

import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Duration
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetPagedHotPostsUseCase(
    private val postRepository: PostRepository,
) {

    suspend operator fun invoke(categoryId: Long?, duration: Duration?, pagingRequest: PagingRequest) =
        postRepository.getHotPosts(categoryId, duration, pagingRequest)

}
