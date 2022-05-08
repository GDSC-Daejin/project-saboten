package commonClient.domain.repository

import common.model.reseponse.post.PostResponse
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getPost(postId: Long): Flow<PostResponse>

}