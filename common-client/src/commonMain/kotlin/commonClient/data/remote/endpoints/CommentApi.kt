package commonClient.data.remote.endpoints

import common.model.request.comment.CommentCreateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.comment.CommentResponse
import common.model.reseponse.paging.PagingResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import commonClient.data.remote.responsePost
import commonClient.domain.entity.PagingRequest
import commonClient.utils.AuthTokenManager
import io.ktor.client.request.*
import org.koin.core.annotation.Single

interface CommentApi : Api {

    override val prefixUrl: String get() = "/api/v1/post"

    suspend fun postComment(
        postId: Long,
        content: String,
    ): ApiResponse<CommentResponse>

    suspend fun getPagedComments(
        postId: Long,
        pageRequest: PagingRequest,
    ): ApiResponse<PagingResponse<CommentResponse>>

}

@Single(binds = [CommentApi::class])
class CommentApiImp(override val authTokenManager: AuthTokenManager) : CommentApi {

    override suspend fun postComment(postId: Long, content: String) = responsePost<CommentResponse>("/$postId/comment") {
        setBody(CommentCreateRequest(content))
    }

    override suspend fun getPagedComments(
        postId: Long,
        pageRequest: PagingRequest,
    ) = responseGet<PagingResponse<CommentResponse>>("/$postId/comment") {
        pageRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    private fun PagingRequest.toParameters() = listOf(
        "page" to if (page != null && page < 0) null else page,
        "size" to size,
        "sort" to sort?.joinToString(","),
    )

}