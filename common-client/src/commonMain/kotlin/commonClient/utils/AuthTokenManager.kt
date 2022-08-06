package commonClient.utils

import com.russhwolf.settings.Settings
import common.model.request.auth.TokenReissueRequest
import common.model.reseponse.auth.JwtTokenResponse
import commonClient.data.remote.endpoints.AuthApi
import commonClient.di.Inject
import commonClient.di.Named
import commonClient.di.Singleton
import commonClient.logger.ClientLogger
import io.ktor.client.plugins.auth.providers.*
import kotlinx.datetime.Clock

@Singleton
class AuthTokenManager @Inject constructor(
    @Named("encrypted") private val settings: Settings
) {

    init {
        val accessToken = accessToken
        val refreshToken = refreshToken
        if (accessToken != null && refreshToken != null) {
            _tokenStorage.add(BearerTokens(accessToken, refreshToken))
        }
    }

    fun setToken(jwtTokenResponse: JwtTokenResponse) {
        settings.putString(KEY_ACCESS_TOKEN, jwtTokenResponse.accessToken)
        settings.putString(KEY_REFRESH_TOKEN, jwtTokenResponse.refreshToken)
        settings.putLong(KEY_EXPIRES_IN, jwtTokenResponse.accessTokenExpiresIn)
        addToken(jwtTokenResponse)
    }

    fun getAccessToken(): String? {
        return settings.getStringOrNull(KEY_ACCESS_TOKEN)
    }

    fun getRefreshToken(): String? {
        return settings.getStringOrNull(KEY_REFRESH_TOKEN)
    }

    private val accessToken: String?
        get() = settings.getStringOrNull(KEY_ACCESS_TOKEN)

    private val refreshToken: String?
        get() = settings.getStringOrNull(KEY_REFRESH_TOKEN)

    fun isTokenExpired() = (settings.getLongOrNull(KEY_EXPIRES_IN) ?: 0L) < Clock.System.now().toEpochMilliseconds()

    fun clear() {
        settings.remove(KEY_ACCESS_TOKEN)
        settings.remove(KEY_REFRESH_TOKEN)
        settings.remove(KEY_EXPIRES_IN)
        _tokenStorage.clear()
    }

    companion object {

        private val _tokenStorage = mutableListOf<BearerTokens>()
        val tokenStorage: List<BearerTokens>
            get() = _tokenStorage

        fun addToken(token: JwtTokenResponse) {
            _tokenStorage.add(BearerTokens(token.accessToken, token.refreshToken))
        }

        const val KEY_ACCESS_TOKEN = "access_token"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_EXPIRES_IN = "expires_in"
    }
}