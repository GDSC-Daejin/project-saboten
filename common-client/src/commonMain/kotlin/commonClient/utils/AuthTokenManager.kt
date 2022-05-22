package commonClient.utils

import com.russhwolf.settings.Settings
import common.model.request.auth.TokenReissueRequest
import common.model.reseponse.auth.JwtTokenResponse
import commonClient.data.remote.endpoints.AuthApi
import commonClient.di.Inject
import commonClient.di.Named
import commonClient.di.Singleton
import commonClient.logger.ClientLogger
import kotlinx.datetime.Clock

@Singleton
class AuthTokenManager @Inject constructor(
    @Named("encrypted") private val settings: Settings
) {

    fun setToken(jwtTokenResponse: JwtTokenResponse) {
        settings.putString(KEY_ACCESS_TOKEN, jwtTokenResponse.accessToken)
        settings.putString(KEY_REFRESH_TOKEN, jwtTokenResponse.refreshToken)
        settings.putLong(KEY_EXPIRES_IN, jwtTokenResponse.accessTokenExpiresIn)
    }

    fun getAccessToken(): String? {
        return settings.getStringOrNull(KEY_ACCESS_TOKEN)
    }

    fun getRefreshToken(): String? {
        return settings.getStringOrNull(KEY_REFRESH_TOKEN)
    }

    fun isTokenExpired() = (settings.getLongOrNull(KEY_EXPIRES_IN) ?: 0L) < Clock.System.now().toEpochMilliseconds()

    companion object {
        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_EXPIRES_IN = "expires_in"
    }
}