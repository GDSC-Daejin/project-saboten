package commonClient.data.cache

import com.russhwolf.settings.coroutines.SuspendSettings
import common.model.reseponse.user.UserInfoResponse
import commonClient.di.Inject
import commonClient.di.Singleton
import kotlinx.coroutines.flow.*
import kotlinx.serialization.json.Json

@Singleton
class MeCache @Inject constructor(
    private val settings: SuspendSettings
) : Cache<UserInfoResponse> {

    private val _me: MutableStateFlow<UserInfoResponse?> = MutableStateFlow(null)
    val me: Flow<UserInfoResponse?> = _me

    override suspend fun save(value: UserInfoResponse) {
        _me.emit(value)
        settings.putString(KEY_CACHE_USER_ME, Json.encodeToString(UserInfoResponse.serializer(), value))
    }

    suspend fun getMe() =
        settings.getStringOrNull(KEY_CACHE_USER_ME)?.let { Json.decodeFromString(UserInfoResponse.serializer(), it) }

    suspend fun flush() {
        settings.remove(KEY_CACHE_USER_ME)
        _me.emit(null)
    }

    companion object {
        private const val KEY_CACHE_USER_ME = "CACHE_USER_ME"
    }
}