package commonClient.data.remote.endpoints

import common.model.reseponse.ApiResponse
import common.model.reseponse.comment.CommentResponse
import common.model.reseponse.paging.PagingResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import io.ktor.client.request.*
import org.koin.core.annotation.Single

interface CommentApi : Api {

    override val prefixUrl: String get() = "/api/v1/post"

    suspend fun getPagedComments(
        postId: Long,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?
    ) : ApiResponse<PagingResponse<CommentResponse>>

}

@Single(binds = [CommentApi::class])
class CommentApiImp : CommentApi {
    override suspend fun getPagedComments(
        postId: Long,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?
    ) = responseGet<PagingResponse<CommentResponse>>("/$postId/comment") {
        parameter("offset", offset)
        parameter("pageNumber", pageNumber)
        parameter("pageSize", pageSize)
    }


}