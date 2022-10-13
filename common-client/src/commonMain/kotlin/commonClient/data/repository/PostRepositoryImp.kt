package commonClient.data.repository

import common.model.reseponse.PagingResponse
import commonClient.data.remote.endpoints.PostApi
import commonClient.domain.entity.post.Post
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.PostRepository
import commonClient.utils.map
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single(binds = [PostRepository::class])
class PostRepositoryImp(
    private val postApi: PostApi
) : PostRepository {

    override fun postsById(postId: Long): Flow<Post> {
        TODO("Not yet implemented")
    }

    override suspend fun getPagedPost(categoryId: Long?, nextKey : Long?): PagingResponse<Post> {
        val response = postApi.getPagedPosts(categoryId, nextKey).data!!
        return response.map { it.toDomain() }
    }
}