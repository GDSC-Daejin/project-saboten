package commonClient.data.repository

import common.model.request.user.UserUpdateRequest
import commonClient.data.cache.MeCache
import commonClient.data.remote.endpoints.UserApi
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@Singleton
class UserRepositoryImp @Inject constructor(
    private val userApi: UserApi,
    private val meCache: MeCache
) : UserRepository {

    override fun getMe() = flow {
        val me = userApi.getMe()
        meCache.save(requireNotNull(me.data))
        emit(requireNotNull(me.data?.toDomain()))
    }

    override fun getUser(id: Long) = flow {
        val user = userApi.getUser(id)
        emit(requireNotNull(user.data?.toDomain()))
    }

    override fun updateUserInfo(userUpdateRequest: UserUpdateRequest) = flow {
        val user = userApi.updateUserInfo(userUpdateRequest)
        meCache.save(requireNotNull(user.data))
        emit(requireNotNull(user.data?.toDomain()))
    }

    override fun observeMe() = meCache.me.map { it?.toDomain() }


}