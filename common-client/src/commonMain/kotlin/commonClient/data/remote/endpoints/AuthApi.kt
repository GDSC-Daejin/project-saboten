package commonClient.data.remote.endpoints

import common.model.request.auth.TokenReissueRequest
import common.model.request.user.UserSignUpRequest
import common.model.reseponse.ApiResponse
 eimport common.model.reseponse.auth.JwtTokenResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responsePost
import commonClient.di.Inject
import commonClient.di.Singleton
import io.ktor.client.*
import io.ktor.client.request.*

interface AuthApi : Api {

    override val prefixUrl: String get() = "/api/v1/auth"

    suspend fun signup(userSignInRequest: UserSignUpRequest): ApiResponse<String>

    suspend fun login(): ApiResponse<JwtTokenResponse>

    suspend fun reissue(tokenReissueRequest: TokenReissueRequest): ApiResponse<JwtTokenResponse>

}

@Singleton
class AuthApiImp @Inject constructor(override val httpClient: HttpClient) : AuthApi {

    override suspend fun signup(userSignInRequest: UserSignUpRequest): ApiResponse<String> = responsePost("/signup") {
        setBody(userSignInRequest)
    }

    override suspend fun login(): ApiResponse<JwtTokenResponse> = responsePost("/login") {

    }

    override suspend fun reissue(tokenReissueRequest: TokenReissueRequest): ApiResponse<JwtTokenResponse> =
        responsePost("/reissue") {
            setBody(tokenReissueRequest)
        }

}