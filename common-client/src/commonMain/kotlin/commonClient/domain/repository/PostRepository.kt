package commonClient.domain.repository

import common.model.reseponse.PagingResponse
import commonClient.domain.entity.post.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    suspend fun postsById(postId: Long): Post

    suspend fun getPagedPost(categoryId : Long?, nextKey : Long?) : PagingResponse<Post>

}