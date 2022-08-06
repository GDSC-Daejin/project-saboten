package commonClient.data.repository

import com.kuuurt.paging.multiplatform.Pager
import commonClient.domain.entity.post.Comment
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.CommentRepository

@Singleton
class CommentRepositoryImp @Inject constructor(

) : CommentRepository {

    override fun getCommentsPager(postId: Long): Pager<Long, Comment> {
        TODO("Not yet implemented")
    }

}