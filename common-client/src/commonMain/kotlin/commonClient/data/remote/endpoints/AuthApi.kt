package commonClient.data.remote.endpoints

import common.model.request.auth.TokenReissueRequest
import common.model.request.user.UserSignUpRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.auth.JwtTokenResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responsePost
import io.ktor.client.request.setBody
import org.koin.core.annotation.Single

interface AuthApi : Api {

    override val prefixUrl: String get() = "/api/v1/auth"

    suspend fun signup(userSignInRequest: UserSignUpRequest): ApiResponse<String>

    suspend fun login(): ApiResponse<JwtTokenResponse>

    suspend fun reissue(tokenReissueRequest: TokenReissueRequest): ApiResponse<JwtTokenResponse>

}

@Single(binds = [AuthApi::class])
class AuthApiImp() : AuthApi {

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