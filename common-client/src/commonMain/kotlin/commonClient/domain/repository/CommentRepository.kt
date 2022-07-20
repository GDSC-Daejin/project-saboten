package commonClient.domain.repository

import com.kuuurt.paging.multiplatform.Pager
import commonClient.domain.entity.post.Comment

interface CommentRepository {

    fun getCommentsPager(postId : Long) : Pager<Long, Comment>

}