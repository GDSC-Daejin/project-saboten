package commonClient.data.repository

import common.model.request.user.UserUpdateRequest
import common.model.reseponse.user.UserInfoResponse
import commonClient.data.LoadState
import commonClient.data.LoadState.Companion.failed
import commonClient.data.LoadState.Companion.loading
import commonClient.data.LoadState.Companion.success
import commonClient.data.cache.MeCache
import commonClient.data.remote.endpoints.UserApi
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.UserRepository
import kotlinx.coroutines.flow.flow

@Singleton
class UserRepositoryImp @Inject constructor(
    private val userApi: UserApi,
    private val meCache: MeCache
) : UserRepository {

    override fun getMe() = flow {
        val me = userApi.getMe()
        meCache.save(requireNotNull(me.data))
        emit(requireNotNull(me.data))
    }

    override fun getUser(id: Long) = flow {
        val user = userApi.getUser(id)
        emit(requireNotNull(user.data))
    }

    override fun updateUserInfo(userUpdateRequest: UserUpdateRequest) = flow {
        val user = userApi.updateUserInfo(userUpdateRequest)
        meCache.save(requireNotNull(user.data))
        emit(requireNotNull(user.data))
    }

    override fun observeMe() = meCache.me


}