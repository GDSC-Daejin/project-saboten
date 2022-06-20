package commonClient.domain.repository

import commonClient.domain.entity.post.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPost(postId: Long): Flow<Post>

}