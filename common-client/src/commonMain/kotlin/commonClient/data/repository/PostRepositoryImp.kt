package commonClient.data.repository

import common.model.reseponse.PagingResponse
import commonClient.data.remote.endpoints.PostApi
import commonClient.di.Inject
import commonClient.domain.entity.post.Post
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.PostRepository
import commonClient.utils.map
import kotlinx.coroutines.flow.Flow

class PostRepositoryImp @Inject constructor(
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