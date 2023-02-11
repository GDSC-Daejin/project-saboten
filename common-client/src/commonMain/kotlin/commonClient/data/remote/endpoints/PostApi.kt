package commonClient.data.remote.endpoints

import common.model.request.post.VoteSelectRequest
import common.model.request.post.create.PostCreateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.paging.NewPagingResponse
import common.model.reseponse.post.PostResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseDelete
import commonClient.data.remote.responseGet
import commonClient.data.remote.responsePost
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Duration
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import org.koin.core.annotation.Single

interface PostApi : Api {

    override val prefixUrl: String get() = "/api/v1/post"

    suspend fun createPost(request: PostCreateRequest): ApiResponse<PostResponse>

    suspend fun likePost(postId: Long): ApiResponse<PostResponse>

    suspend fun votePost(postId: Long, voteSelectRequest: VoteSelectRequest): ApiResponse<PostResponse>

    suspend fun scrapPost(postId: Long): ApiResponse<PostResponse>

    suspend fun deletePost(postId: Int): ApiResponse<String>

    suspend fun getPost(postId: Long): ApiResponse<PostResponse>

    suspend fun getHotPosts(
        categoryId: Long?,
        duration: Duration?,
        pagingRequest: PagingRequest,
    ): ApiResponse<NewPagingResponse<PostResponse>>

    suspend fun getMyPosts(pagingRequest: PagingRequest): ApiResponse<NewPagingResponse<PostResponse>>

    suspend fun getMyScrappedPosts(pagingRequest: PagingRequest): ApiResponse<NewPagingResponse<PostResponse>>

    suspend fun getMyVotedPosts(pagingRequest: PagingRequest): ApiResponse<NewPagingResponse<PostResponse>>

    suspend fun getRecentPosts(pagingRequest: PagingRequest): ApiResponse<NewPagingResponse<PostResponse>>

    suspend fun getSearchPosts(searchText: String, pagingRequest: PagingRequest): ApiResponse<NewPagingResponse<PostResponse>>
}

@Single(binds = [PostApi::class])
class PostApiImp : PostApi {

    override suspend fun createPost(request: PostCreateRequest) =
        responsePost<PostResponse> { setBody(request) }

    override suspend fun deletePost(postId: Int) =
        responseDelete<String>(postId)

    override suspend fun getPost(postId: Long) =
        responseGet<PostResponse>("/$postId")

    override suspend fun likePost(postId: Long): ApiResponse<PostResponse> {
        return responsePost("/$postId/like")
    }

    override suspend fun votePost(postId: Long, voteSelectRequest: VoteSelectRequest): ApiResponse<PostResponse> {
        return responsePost("/$postId/vote") { setBody(voteSelectRequest) }
    }

    override suspend fun scrapPost(postId: Long): ApiResponse<PostResponse> {
        return responsePost("/$postId/scrap")
    }

    override suspend fun getHotPosts(
        categoryId: Long?,
        duration: Duration?,
        pagingRequest: PagingRequest,
    ) = responseGet<NewPagingResponse<PostResponse>>("/hot") {
        parameter("categoryId", categoryId)
        parameter("duration", duration?.name)
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getRecentPosts(pagingRequest: PagingRequest) = responseGet<NewPagingResponse<PostResponse>>("/recent") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getSearchPosts(
        searchText: String,
        pagingRequest: PagingRequest,
    ) = responseGet<NewPagingResponse<PostResponse>>("/search/$searchText") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getMyPosts(pagingRequest: PagingRequest) = responseGet<NewPagingResponse<PostResponse>>("/my") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getMyScrappedPosts(pagingRequest: PagingRequest) = responseGet<NewPagingResponse<PostResponse>>("/my/scrap") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getMyVotedPosts(pagingRequest: PagingRequest) = responseGet<NewPagingResponse<PostResponse>>("/my/voted") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    private fun PagingRequest.toParameters() = listOf(
        "offset" to offset,
        "pageNumber" to pageNumber,
        "pageSize" to pageSize,
    )

}