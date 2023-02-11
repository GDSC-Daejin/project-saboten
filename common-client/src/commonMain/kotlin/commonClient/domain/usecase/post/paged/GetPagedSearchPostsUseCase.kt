package commonClient.domain.usecase.post.paged

import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Duration
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetPagedSearchPostsUseCase(
    private val postRepository: PostRepository,
) {

    suspend operator fun invoke(searchQuery : String, pagingRequest: PagingRequest) =
        postRepository.getSearchPosts(searchQuery, pagingRequest)

}
