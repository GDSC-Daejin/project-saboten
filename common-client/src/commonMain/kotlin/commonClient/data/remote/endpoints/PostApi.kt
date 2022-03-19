package commonClient.data.remote.endpoints

import common.model.request.post.PostCreateRequest
import common.model.request.post.PostUpdateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.post.Post
import commonClient.data.remote.*
import commonClient.di.Inject
import commonClient.di.Singleton
import io.ktor.client.*
import io.ktor.client.request.*

interface PostApi : Api {

    override val prefixUrl: String get() = "/api/v1/post/"

    suspend fun createPost(request: PostCreateRequest): ApiResponse<Post>

    suspend fun updatePost(postId: Int, request: PostUpdateRequest): ApiResponse<Post>

    suspend fun deletePost(postId: Int): ApiResponse<String>

    suspend fun getPost(postId: Int): ApiResponse<Post>

}

@Singleton
class PostApiImp @Inject constructor(override val httpClient: HttpClient) : PostApi {

    override suspend fun createPost(request: PostCreateRequest) =
        responsePost<Post> { setBody(request) }

    override suspend fun updatePost(postId: Int, request: PostUpdateRequest) =
        responsePatch<Post>(postId) { setBody(request) }

    override suspend fun deletePost(postId: Int) =
        responseDelete<String>(postId)

    override suspend fun getPost(postId: Int) =
        responseGet<Post>(postId)

}