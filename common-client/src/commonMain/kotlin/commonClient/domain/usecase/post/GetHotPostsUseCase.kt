package commonClient.domain.usecase.post

import common.model.reseponse.post.PostResponse
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class GetHotPostsUseCase(private val postRepository: PostRepository) {

    suspend operator fun invoke(
        category: Category,
        duration: Duration,
    ): List<Post> =
        postRepository
        .getPagedHotPost(category.id, duration, null, null, pageSize = PAGE_ITEM_SIZE)
        .content

    companion object {
        private const val PAGE_ITEM_SIZE = 5
    }

}