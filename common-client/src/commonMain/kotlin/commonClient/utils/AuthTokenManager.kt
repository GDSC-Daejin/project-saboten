package commonClient.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import common.model.reseponse.auth.JwtTokenResponse
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.annotation.Single

@Single
class AuthTokenManager(
    private val settings: DataStore<Preferences>,
) {

    init {
        CoroutineScope(Dispatchers.Default).launch {
            val accessToken = accessToken()
            val refreshToken = refreshToken()
            if (accessToken != null && refreshToken != null) {
                _tokenStorage.add(BearerTokens(accessToken, refreshToken))
            }
        }
    }

    suspend fun setToken(jwtTokenResponse: JwtTokenResponse) {
        settings.edit {
            it[KEY_ACCESS_TOKEN] = jwtTokenResponse.accessToken
            it[KEY_REFRESH_TOKEN] = jwtTokenResponse.refreshToken
            it[KEY_EXPIRES_IN] = jwtTokenResponse.accessTokenExpiresIn
        }
        addToken(jwtTokenResponse)
    }

    suspend fun accessToken() : String? {
        return settings.data.map { it[KEY_ACCESS_TOKEN] }.firstOrNull()
    }

    suspend fun refreshToken() : String? {
        return settings.data.map { it[KEY_REFRESH_TOKEN] }.firstOrNull()
    }

    suspend fun isTokenExpired(): Boolean {
        val expiresIn = settings.data.map { it[KEY_EXPIRES_IN] }.firstOrNull() ?: 0L
        return expiresIn < Clock.System.now().toEpochMilliseconds()
    }

    suspend fun clear() {
        settings.edit {
            it.remove(KEY_ACCESS_TOKEN)
            it.remove(KEY_REFRESH_TOKEN)
            it.remove(KEY_EXPIRES_IN)
        }
        _tokenStorage.clear()
    }

    companion object {

        @Suppress("ObjectPropertyName")
        private val _tokenStorage = mutableListOf<BearerTokens>()
        val tokenStorage: List<BearerTokens>
            get() = _tokenStorage

        fun addToken(token: JwtTokenResponse) {
            _tokenStorage.add(BearerTokens(token.accessToken, token.refreshToken))
        }

        val KEY_ACCESS_TOKEN = stringPreferencesKey("access_token")
        val KEY_REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val KEY_EXPIRES_IN = longPreferencesKey("expires_in")
    }
}