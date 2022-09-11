package commonClient.data.remote.endpoints

import common.model.request.post.create.PostCreateRequest
import common.model.request.post.update.PostUpdateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.PagingResponse
import common.model.reseponse.post.PostResponse
import commonClient.data.remote.*
import commonClient.di.Inject
import commonClient.di.Singleton
import io.ktor.client.*
import io.ktor.client.request.*

interface PostApi : Api {

    override val prefixUrl: String get() = "/api/v1/post"

    suspend fun createPost(request: PostCreateRequest): ApiResponse<PostResponse>

    suspend fun updatePost(postId: Int, request: PostUpdateRequest): ApiResponse<PostResponse>

    suspend fun deletePost(postId: Int): ApiResponse<String>

    suspend fun getPost(postId: Int): ApiResponse<PostResponse>

    suspend fun getPagedPosts(categoryId: Long?, nextKey : Long?): ApiResponse<PagingResponse<PostResponse>>

}

@Singleton
class PostApiImp @Inject constructor() : PostApi {

    override suspend fun createPost(request: PostCreateRequest) =
        responsePost<PostResponse> { setBody(request) }

    override suspend fun updatePost(postId: Int, request: PostUpdateRequest) =
        responsePatch<PostResponse>(postId) { setBody(request) }

    override suspend fun deletePost(postId: Int) =
        responseDelete<String>(postId)

    override suspend fun getPost(postId: Int) =
        responseGet<PostResponse>(postId)

    override suspend fun getPagedPosts(categoryId: Long?, nextKey: Long?) =
        responseGet<PagingResponse<PostResponse>> {
            parameter("category_id", categoryId)
        }

}