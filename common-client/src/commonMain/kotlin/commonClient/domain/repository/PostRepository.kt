package commonClient.domain.repository

import common.model.reseponse.PagingData
import commonClient.domain.entity.post.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun postsById(postId: Long): Flow<Post>

    suspend fun getPagedPost(categoryId : Long?) : PagingData<Post>

}