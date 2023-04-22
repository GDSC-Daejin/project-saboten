package commonClient.domain.repository

import common.model.reseponse.paging.PagingResponse
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Comment

interface CommentRepository {

    suspend fun postComment(
        postId: Long,
        content: String
    ) : Comment

    suspend fun getPagedComment(
        postId: Long,
        pageRequest: PagingRequest
    ) : PagingResponse<Comment>

}