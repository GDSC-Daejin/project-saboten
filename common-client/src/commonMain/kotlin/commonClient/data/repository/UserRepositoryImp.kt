package commonClient.data.repository

import common.model.request.user.UserUpdateRequest
import commonClient.data.cache.MeCache
import commonClient.data.remote.endpoints.UserApi
import commonClient.domain.entity.user.UserInfo
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single(binds = [UserRepository::class])
class UserRepositoryImp(
    private val userApi: UserApi,
    private val meCache: MeCache
) : UserRepository {

    override suspend fun getMe() : UserInfo {
        val me = userApi.getMe()
        meCache.save(requireNotNull(me.data))
        return requireNotNull(me.data?.toDomain())
    }

    override suspend fun getUser(id: Long) : UserInfo {
        val user = userApi.getUser(id)
        return requireNotNull(user.data?.toDomain())
    }

    override suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest) : UserInfo {
        val user = userApi.updateUserInfo(userUpdateRequest)
        meCache.save(requireNotNull(user.data))
        return requireNotNull(user.data?.toDomain())
    }

    override fun observeMe() = meCache.me.map { it?.toDomain() }


}