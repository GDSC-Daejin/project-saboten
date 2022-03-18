@file:OptIn(ExperimentalSettingsApi::class)

package commonClient.data.cache

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import common.model.reseponse.user.UserInfo
import commonClient.di.Inject
import commonClient.di.Singleton
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

@Singleton
class MeCache @Inject constructor(
    private val settings: SuspendSettings
) : Cache<UserInfo> {

    private val _me: MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    val me: Flow<UserInfo?> = _me

    override suspend fun save(value: UserInfo) {
        _me.emit(value)
        settings.putString(KEY_CACHE_USER_ME, Json.encodeToString(UserInfo.serializer(), value))
    }

    suspend fun getMe() =
        settings.getStringOrNull(KEY_CACHE_USER_ME)?.let { Json.decodeFromString(UserInfo.serializer(), it) }

    override suspend fun delete(id: Long) {
        settings.remove(KEY_CACHE_USER_ME)
        _me.emit(null)
    }

    companion object {
        private const val KEY_CACHE_USER_ME = "CACHE_USER_ME"
    }
}