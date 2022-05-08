package commonClient.domain.repository

import common.model.reseponse.post.Post
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPost(postId: Long): Flow<Post>

}