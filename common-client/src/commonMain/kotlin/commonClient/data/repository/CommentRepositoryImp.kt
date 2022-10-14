package commonClient.data.repository

import com.kuuurt.paging.multiplatform.Pager
import commonClient.domain.entity.post.Comment
import commonClient.domain.repository.CommentRepository
import org.koin.core.annotation.Single

@Single(binds = [CommentRepository::class])
class CommentRepositoryImp(

) : CommentRepository {

    override fun getCommentsPager(postId: Long): Pager<Long, Comment> {
        TODO("Not yet implemented")
    }

}