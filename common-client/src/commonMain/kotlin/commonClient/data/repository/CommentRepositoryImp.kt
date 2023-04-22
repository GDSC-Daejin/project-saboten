package commonClient.data.repository

import common.model.reseponse.paging.PagingResponse
import commonClient.data.remote.endpoints.CommentApi
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Comment
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.CommentRepository
import commonClient.utils.map
import org.koin.core.annotation.Single

@Single(binds = [CommentRepository::class])
class CommentRepositoryImp(
    private val commentApi: CommentApi
) : CommentRepository {

    override suspend fun postComment(postId: Long, content: String): Comment {
        return commentApi.postComment(postId, content).data!!.toDomain()
    }

    override suspend fun getPagedComment(
        postId: Long,
        pageRequest: PagingRequest
    ): PagingResponse<Comment> {
        val response = commentApi.getPagedComments(postId, pageRequest).data!!
        return response.map { it.toDomain() }
    }

}