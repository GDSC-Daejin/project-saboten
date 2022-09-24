package commonClient.data.repository

import common.model.request.auth.TokenReissueRequest
import commonClient.data.remote.endpoints.AuthApi
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.entity.auth.JwtToken
import commonClient.domain.repository.AuthRepository
import commonClient.utils.AuthTokenManager
import kotlinx.coroutines.flow.flow

@Singleton
class AuthRepositoryImp @Inject constructor(
    private val authApi: AuthApi,
    private val authTokenManager: AuthTokenManager
) : AuthRepository {

    override fun refreshToken(forceRefresh: Boolean) = flow {

        val accessToken = authTokenManager.accessToken
        val refreshToken = authTokenManager.refreshToken

        if (accessToken == null || refreshToken == null) {
            emit(null)
            return@flow
        }

        if (authTokenManager.isTokenExpired()) {
            val refreshTokenRequest = TokenReissueRequest(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
            emit(authApi.reissue(refreshTokenRequest).data?.run {
                JwtToken(
                    grantType = grantType,
                    accessToken = accessToken,
                    refreshToken = refreshToken,
                    accessTokenExpiresIn = accessTokenExpiresIn
                )
            })
        } else {
            emit(null)
        }
    }
}