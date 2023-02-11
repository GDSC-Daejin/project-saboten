package commonClient.domain.usecase.post

import commonClient.domain.entity.PagingRequest
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetRecentPostsUseCase(
    private val postRepository: PostRepository,
) {

    suspend operator fun invoke() = postRepository.getRecentPosts(
        PagingRequest(null, size = PAGE_ITEM_SIZE)
    ).data

    companion object {
        private const val PAGE_ITEM_SIZE = 5
    }


}
