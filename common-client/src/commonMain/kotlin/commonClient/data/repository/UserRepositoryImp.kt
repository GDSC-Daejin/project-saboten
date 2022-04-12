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
        emit(loading())
        userApi
            .runCatching { getMe() }
            .onFailure { emit(failed(it, meCache.getMe())) }
            .onSuccess {
                meCache.save(it.data)
                emit(success(it.data))
            }
    }

    override fun getUser(id: Long) = flow<LoadState<UserInfoResponse>> {
        emit(loading())
        userApi
            .runCatching { getUser(id) }
            .onFailure { emit(failed(it)) }
            .onSuccess { emit(success(it.data)) }
    }

    override fun updateUserInfo(userUpdateRequest: UserUpdateRequest) = flow {
        emit(loading())
        userApi
            .runCatching { updateUserInfo(userUpdateRequest) }
            .onFailure { emit(failed(it, meCache.getMe())) }
            .onSuccess {
                meCache.save(it.data)
                emit(success(it.data))
            }
    }

    override fun observeMe() = meCache.me


}