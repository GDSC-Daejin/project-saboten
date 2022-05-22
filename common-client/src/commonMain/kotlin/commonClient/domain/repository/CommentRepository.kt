package commonClient.domain.repository

import com.kuuurt.paging.multiplatform.Pager
import common.model.reseponse.post.CommentResponse

interface CommentRepository {

    fun getCommentsPager(postId : Long) : Pager<Long, CommentResponse>

}