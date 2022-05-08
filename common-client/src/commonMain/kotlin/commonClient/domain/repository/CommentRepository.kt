package commonClient.domain.repository

import com.kuuurt.paging.multiplatform.Pager
import common.model.reseponse.post.Comment
import kotlinx.coroutines.flow.Flow

interface CommentRepository {

    fun getCommentsPager(postId : Long) : Pager<Long, Comment>

}