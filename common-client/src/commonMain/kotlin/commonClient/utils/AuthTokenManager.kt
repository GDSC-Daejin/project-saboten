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

    private val currentTimeInMillis get() = Clock.System.now().toEpochMilliseconds()

    init {
        val accessToken = accessToken
        val refreshToken = refreshToken
        if (accessToken != null && refreshToken != null) {
            _tokenStorage.add(BearerTokens(accessToken, refreshToken))
        }
    }

    fun setToken(token: JwtTokenResponse) {
        settings.putString(KEY_ACCESS_TOKEN, token.accessToken)
        settings.putString(KEY_REFRESH_TOKEN, token.refreshToken)
        settings.putLong(KEY_EXPIRE_AT, currentTimeInMillis + token.accessTokenExpiresIn * 1000)
        addToken(token)
    }

    private val accessToken: String?
        get() = settings.getStringOrNull(KEY_ACCESS_TOKEN)

    private val refreshToken: String?
        get() = settings.getStringOrNull(KEY_REFRESH_TOKEN)

    fun isTokenExpired(): Boolean {
        return (settings.getLongOrNull(KEY_EXPIRE_AT) ?: 0L) <= currentTimeInMillis
    }

    fun clear() {
        settings.remove(KEY_ACCESS_TOKEN)
        settings.remove(KEY_REFRESH_TOKEN)
        settings.remove(KEY_EXPIRE_AT)
        _tokenStorage.clear()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_EXPIRE_AT = "expire_at"

        private val _tokenStorage = mutableListOf<BearerTokens>()
        val tokenStorage: List<BearerTokens>
            get() = _tokenStorage

        fun addToken(token: JwtTokenResponse) {
            _tokenStorage.add(BearerTokens(token.accessToken, token.refreshToken))
        }
    }
}