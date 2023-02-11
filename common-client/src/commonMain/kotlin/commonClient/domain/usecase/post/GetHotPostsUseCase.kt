package commonClient.domain.usecase.post

import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetHotPostsUseCase(private val postRepository: PostRepository) {

    suspend operator fun invoke(
        category: Category,
        duration: Duration,
    ): List<Post> =
        postRepository
            .getHotPosts(
                category.id, duration,
                PagingRequest(null, size = PAGE_ITEM_SIZE)
            )
            .data

    companion object {
        private const val PAGE_ITEM_SIZE = 5
    }

}