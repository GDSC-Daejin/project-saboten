package commonClient.data.repository

import com.kuuurt.paging.multiplatform.Pager
import common.model.reseponse.paging.NewPagingResponse
import commonClient.data.remote.endpoints.CommentApi
import commonClient.domain.entity.post.Comment
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.CommentRepository
import commonClient.utils.map
import org.koin.core.annotation.Single

@Single(binds = [CommentRepository::class])
class CommentRepositoryImp(
    private val commentApi: CommentApi
) : CommentRepository {

    override fun getCommentsPager(postId: Long): Pager<Long, Comment> {
        TODO("Not yet implemented")
    }

    override suspend fun getPagedComment(
        postId: Long,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?
    ): NewPagingResponse<Comment> {
        val response = commentApi.getPagedComments(postId, offset, pageNumber, pageSize).data!!
        return response.map { it.toDomain() }
    }

}