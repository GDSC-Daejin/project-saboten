package commonClient.domain.usecase.post

import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Duration
import commonClient.domain.entity.post.Post
import commonClient.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single
class GetHotPostsUseCase(private val postRepository: PostRepository) {

    operator fun invoke(
        category: Category,
        duration: Duration
    ) : Flow<List<Post>> = flow {

    }

}