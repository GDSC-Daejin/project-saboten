package commonClient.data.cache

import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import common.model.reseponse.user.UserInfo
import commonClient.di.Inject
import commonClient.di.Named
import commonClient.di.Singleton
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

@Singleton
class MeCache @Inject constructor(
    @Named("encrypted") private val settings: Settings
) : Cache<UserInfo> {

    private val _me: MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    val me: Flow<UserInfo?> = _me

    override suspend fun save(value: UserInfo) {
        _me.emit(value)
        settings.putString(KEY_CACHE_USER_ME, Json.encodeToString(UserInfo.serializer(), value))
    }

    suspend fun getMe() =
        settings.getStringOrNull(KEY_CACHE_USER_ME)?.let { Json.decodeFromString(UserInfo.serializer(), it) }

    suspend fun flush() {
        settings.remove(KEY_CACHE_USER_ME)
        _me.emit(null)
    }

    companion object {
        private const val KEY_CACHE_USER_ME = "CACHE_USER_ME"
    }
}