package commonClient.utils

import com.russhwolf.settings.Settings
import common.model.request.auth.TokenReissueRequest
import common.model.reseponse.auth.JwtToken
import commonClient.data.remote.endpoints.AuthApi
import commonClient.di.Inject
import commonClient.di.Named
import commonClient.di.Singleton
import commonClient.logger.ClientLogger
import kotlinx.datetime.Clock

@Singleton
class AuthTokenManager @Inject constructor(
    @Named("encrypted") private val settings: Settings,
    private val authApi: AuthApi
) {

    fun setToken(jwtToken: JwtToken) {
        settings.putString(KEY_ACCESS_TOKEN, jwtToken.accessToken)
        settings.putString(KEY_REFRESH_TOKEN, jwtToken.refreshToken)
        settings.putLong(KEY_EXPIRES_IN, jwtToken.accessTokenExpiresIn)
    }

    fun getToken(): String? {
        return settings.getStringOrNull(KEY_ACCESS_TOKEN)
    }

    private val isTokenExpired
        get() = (settings.getLongOrNull(KEY_EXPIRES_IN) ?: 0L) < Clock.System.now().toEpochMilliseconds()

    suspend fun refreshTokenIfNeeded(): Result<Unit> {
        if (getToken() == null) return Result.success(Unit)
        if (!isTokenExpired) return Result.success(Unit)

        return authApi.runCatching {
            val response = reissue(
                TokenReissueRequest(
                    accessToken = settings.getString(KEY_ACCESS_TOKEN),
                    refreshToken = settings.getString(KEY_REFRESH_TOKEN)
                )
            )
            setToken(response.data)
        }.onFailure(ClientLogger::e)
    }

    companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_EXPIRES_IN = "expires_in"
    }
}