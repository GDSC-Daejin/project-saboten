package commonClient.domain.usecase.post.paged

import commonClient.domain.entity.PagingRequest
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetPagedScrappedPostsUseCase(
    private val postRepository: PostRepository,
) {

    suspend operator fun invoke(pagingRequest: PagingRequest) =
        postRepository.getMyScrappedPosts(pagingRequest)

}
