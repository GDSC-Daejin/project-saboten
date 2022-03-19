package commonClient.data.remote.endpoints

import common.model.request.user.UserSignInRequest
import common.model.reseponse.ApiResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responsePost
import commonClient.di.Inject
import commonClient.di.Singleton
import io.ktor.client.*
import io.ktor.client.request.*

interface AuthApi : Api {

    override val prefixUrl: String get() = "/api/v1/users"

    suspend fun login(): ApiResponse<String>

    suspend fun signIn(request: UserSignInRequest): ApiResponse<String>

    suspend fun signOut(): ApiResponse<String>

}

@Singleton
class AuthApiImp @Inject constructor(override val httpClient: HttpClient) : AuthApi {

    override suspend fun login(): ApiResponse<String> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(request: UserSignInRequest) = responsePost<String>("/signin") { setBody(request) }

    override suspend fun signOut(): ApiResponse<String> {
        TODO("Not yet implemented")
    }

}