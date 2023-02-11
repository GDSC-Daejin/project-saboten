package commonClient.domain.repository

import com.kuuurt.paging.multiplatform.Pager
import common.model.reseponse.paging.PagingResponse
import commonClient.domain.entity.post.Comment

interface CommentRepository {

    fun getCommentsPager(postId: Long): Pager<Long, Comment>

    suspend fun getPagedComment(
        postId: Long,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?
    ) : PagingResponse<Comment>

}