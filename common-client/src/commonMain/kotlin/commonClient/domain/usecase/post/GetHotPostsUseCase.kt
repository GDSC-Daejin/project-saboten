package commonClient.domain.usecase.post

import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetHotPostsUseCase(private val postRepository: PostRepository) {

    suspend operator fun invoke(
        categoryId: Long = 10L,
        duration: Duration,
    ): List<Post> =
        postRepository
            .getHotPosts(
                categoryId, duration,
                PagingRequest(null, size = PAGE_ITEM_SIZE)
            )
            .data

    companion object {
        private const val PAGE_ITEM_SIZE = 5
    }

}