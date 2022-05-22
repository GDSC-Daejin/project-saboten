package commonClient.data.repository

import common.model.request.auth.TokenReissueRequest
import common.model.reseponse.auth.JwtTokenResponse
import commonClient.data.LoadState
import commonClient.data.remote.endpoints.AuthApi
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.AuthRepository
import commonClient.utils.AuthTokenManager
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.flow.flow

@Singleton
class AuthRepositoryImp @Inject constructor(
    private val authApi: AuthApi,
    private val authTokenManager: AuthTokenManager
) : AuthRepository {

    override fun refreshToken(forceRefresh: Boolean) = flow {

        val accessToken = authTokenManager.getAccessToken()
        val refreshToken =
            authTokenManager.getRefreshToken()

        if (accessToken == null || refreshToken == null) {
            emit(null)
            return@flow
        }

        if (authTokenManager.isTokenExpired()) {
            val refreshTokenRequest = TokenReissueRequest(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
            emit(requireNotNull(authApi.reissue(refreshTokenRequest).data))
        } else {
            emit(null)
        }
    }
}