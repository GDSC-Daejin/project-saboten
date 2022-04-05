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
import kotlin.jvm.Synchronized

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

    fun getToken(): String {
        return settings.getString(KEY_ACCESS_TOKEN)
    }

    val isTokenExpired
        get() =
            (settings.getLongOrNull(KEY_EXPIRES_IN) ?: 0L) < Clock.System.now().toEpochMilliseconds()

    suspend fun refreshToken() {
        if (!isTokenExpired) return

        authApi.runCatching {
            reissue(
                TokenReissueRequest(
                    accessToken = settings.getString(KEY_ACCESS_TOKEN),
                    refreshToken = settings.getString(KEY_REFRESH_TOKEN)
                )
            )
        }.onSuccess { response ->
            setToken(response.data)
        }.onFailure {
            ClientLogger.e(it)
        }

    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_EXPIRES_IN = "expires_in"
    }
}