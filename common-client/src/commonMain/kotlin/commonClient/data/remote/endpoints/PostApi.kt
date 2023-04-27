package commonClient.data.remote.endpoints

import common.model.request.post.VoteSelectRequest
import common.model.request.post.create.PostCreateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.paging.PagingResponse
import common.model.reseponse.post.PostResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseDelete
import commonClient.data.remote.responseGet
import commonClient.data.remote.responsePost
import commonClient.domain.entity.PagingRequest
import commonClient.domain.entity.post.Duration
import commonClient.utils.AuthTokenManager
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import org.koin.core.annotation.Single

interface PostApi : Api {

    override val prefixUrl: String get() = "/api/v1/post"

    suspend fun createPost(request: PostCreateRequest): ApiResponse<PostResponse>

    suspend fun likePost(postId: Long): ApiResponse<PostResponse>

    suspend fun votePost(postId: Long, voteSelectRequest: VoteSelectRequest): ApiResponse<PostResponse>

    suspend fun scrapPost(postId: Long): ApiResponse<PostResponse>

    suspend fun deletePost(postId: Long): ApiResponse<String>

    suspend fun getPost(postId: Long): ApiResponse<PostResponse>

    suspend fun getPosts(categoryId: Long?, pagingRequest: PagingRequest): ApiResponse<PagingResponse<PostResponse>>

    suspend fun getHotPosts(
        categoryId: Long?,
        duration: Duration?,
        pagingRequest: PagingRequest,
    ): ApiResponse<PagingResponse<PostResponse>>

    suspend fun getMyPosts(pagingRequest: PagingRequest): ApiResponse<PagingResponse<PostResponse>>

    suspend fun getMyScrappedPosts(pagingRequest: PagingRequest): ApiResponse<PagingResponse<PostResponse>>

    suspend fun getMyVotedPosts(pagingRequest: PagingRequest): ApiResponse<PagingResponse<PostResponse>>

    suspend fun getRecentPosts(pagingRequest: PagingRequest): ApiResponse<PagingResponse<PostResponse>>

    suspend fun getSearchPosts(searchText: String, pagingRequest: PagingRequest): ApiResponse<PagingResponse<PostResponse>>
}

@Single(binds = [PostApi::class])
class PostApiImp(override val authTokenManager: AuthTokenManager) : PostApi {

    override suspend fun createPost(request: PostCreateRequest) =
        responsePost<PostResponse> { setBody(request) }

    override suspend fun deletePost(postId: Long) =
        responseDelete<String>("/$postId")

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

    override suspend fun getPosts(categoryId: Long?, pagingRequest: PagingRequest): ApiResponse<PagingResponse<PostResponse>> = responseGet("/") {
        parameter("categoryId", categoryId)
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getHotPosts(
        categoryId: Long?,
        duration: Duration?,
        pagingRequest: PagingRequest,
    ) = responseGet<PagingResponse<PostResponse>>("/debate") {
        parameter("categoryId", categoryId)
        parameter("duration", duration?.name ?: "ALL")
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getRecentPosts(pagingRequest: PagingRequest) = responseGet<PagingResponse<PostResponse>>("/recent") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getSearchPosts(
        searchText: String,
        pagingRequest: PagingRequest,
    ) = responseGet<PagingResponse<PostResponse>>("/search") {
        parameter("searchText", searchText)
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getMyPosts(pagingRequest: PagingRequest) = responseGet<PagingResponse<PostResponse>>("/my") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getMyScrappedPosts(pagingRequest: PagingRequest) = responseGet<PagingResponse<PostResponse>>("/my/scrap") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    override suspend fun getMyVotedPosts(pagingRequest: PagingRequest) = responseGet<PagingResponse<PostResponse>>("/my/voted") {
        pagingRequest.toParameters().forEach { (key, value) -> parameter(key, value) }
    }

    private fun PagingRequest.toParameters() = listOf(
        "page" to if (page != null && page < 0) null else page,
        "size" to size,
        "sort" to sort?.joinToString(","),
    )

}