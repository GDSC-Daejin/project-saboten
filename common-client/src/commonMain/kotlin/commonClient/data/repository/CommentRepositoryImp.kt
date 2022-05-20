package commonClient.data.repository

import com.kuuurt.paging.multiplatform.Pager
import common.model.reseponse.post.Comment
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.CommentRepository
import commonClient.utils.createPager

@Singleton
class CommentRepositoryImp @Inject constructor(

) : CommentRepository {

    override fun getCommentsPager(postId: Long): Pager<Long, Comment> = createPager {
        TODO("Not yet implemented")
    }

}