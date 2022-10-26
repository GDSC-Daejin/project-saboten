package commonClient.data.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import common.model.reseponse.user.UserInfoResponse
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single

@Single
class MeCache(
    private val settings: DataStore<Preferences>,
) : Cache<UserInfoResponse> {

    private val _me: MutableStateFlow<UserInfoResponse?> = MutableStateFlow(null)
    val me: Flow<UserInfoResponse?> = _me

    override suspend fun save(value: UserInfoResponse) {
        _me.emit(value)
        settings.edit { it[KEY_CACHE_USER_ME] = Json.encodeToString(UserInfoResponse.serializer(), value) }
    }

    suspend fun getMe() =
        settings.data.map { it[KEY_CACHE_USER_ME] }.firstOrNull()?.let { Json.decodeFromString(UserInfoResponse.serializer(), it) }

    suspend fun flush() {
        settings.edit { it.remove(KEY_CACHE_USER_ME) }
        _me.emit(null)
    }

    companion object {
        private val KEY_CACHE_USER_ME = stringPreferencesKey("CACHE_USER_ME")
    }
}