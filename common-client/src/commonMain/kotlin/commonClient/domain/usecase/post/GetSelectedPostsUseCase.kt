package commonClient.domain.usecase.post

import commonClient.domain.entity.PagingRequest
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetSelectedPostsUseCase(
    private val postRepository: PostRepository,
) {

    suspend operator fun invoke() = postRepository.getRecentPosts(
        PagingRequest(null, null, pageSize = PAGE_ITEM_SIZE)
    ).content

    companion object {
        private const val PAGE_ITEM_SIZE = 5
    }

}
