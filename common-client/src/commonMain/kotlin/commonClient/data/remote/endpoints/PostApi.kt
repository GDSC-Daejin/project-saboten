package commonClient.data.remote.endpoints

import common.model.request.post.create.PostCreateRequest
import common.model.request.post.update.PostUpdateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.PagingResponse
import common.model.reseponse.paging.NewPagingResponse
import common.model.reseponse.post.PostResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseDelete
import commonClient.data.remote.responseGet
import commonClient.data.remote.responsePatch
import commonClient.data.remote.responsePost
import commonClient.domain.entity.post.Duration
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import org.koin.core.annotation.Single

interface PostApi : Api {

    override val prefixUrl: String get() = "/api/v1/post"

    suspend fun createPost(request: PostCreateRequest): ApiResponse<PostResponse>

    suspend fun updatePost(postId: Int, request: PostUpdateRequest): ApiResponse<PostResponse>

    suspend fun deletePost(postId: Int): ApiResponse<String>

    suspend fun getPost(postId: Long): ApiResponse<PostResponse>

    suspend fun getPagedPosts(
        categoryId: Long?,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?,
    ): ApiResponse<NewPagingResponse<PostResponse>>

    suspend fun getPagedHotPosts(
        categoryId: Long?,
        duration: Duration?,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?,
    ): ApiResponse<NewPagingResponse<PostResponse>>

    suspend fun getPagedSearchPosts(
        searchText: String,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?,
    ): ApiResponse<NewPagingResponse<PostResponse>>
}

@Single(binds = [PostApi::class])
class PostApiImp : PostApi {

    override suspend fun createPost(request: PostCreateRequest) =
        responsePost<PostResponse> { setBody(request) }

    override suspend fun updatePost(postId: Int, request: PostUpdateRequest) =
        responsePatch<PostResponse>(postId) { setBody(request) }

    override suspend fun deletePost(postId: Int) =
        responseDelete<String>(postId)

    override suspend fun getPost(postId: Long) =
        responseGet<PostResponse>("/$postId")

    override suspend fun getPagedPosts(
        categoryId: Long?,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?,
    ) = responseGet<NewPagingResponse<PostResponse>> {
        parameter("categoryId", categoryId)
    }

    override suspend fun getPagedHotPosts(
        categoryId: Long?,
        duration: Duration?,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?,
    ) = responseGet<NewPagingResponse<PostResponse>>("/hot") {
        parameter("categoryId", categoryId)
        parameter("duration", duration?.name)
        parameter("offset", offset)
        parameter("pageNumber", pageNumber)
        parameter("pageSize", pageSize)
    }

    override suspend fun getPagedSearchPosts(
        searchText: String,
        offset: Int?,
        pageNumber: Int?,
        pageSize: Int?,
    ) = responseGet<NewPagingResponse<PostResponse>>("/search/$searchText") {
        parameter("offset", offset)
        parameter("pageNumber", pageNumber)
        parameter("pageSize", pageSize)
    }

}