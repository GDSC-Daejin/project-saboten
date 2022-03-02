package commonClient.data.repository

import common.model.User
import commonClient.data.LoadState
import commonClient.data.LoadState.Companion.failed
import commonClient.data.LoadState.Companion.loading
import commonClient.data.LoadState.Companion.success
import commonClient.data.remote.UserApi
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class UserRepositoryImp
@Inject constructor(private val userApi: UserApi) : UserRepository {

    override fun getMe() = flow<LoadState<User>> {
        emit(loading())
        userApi.runCatching { getMe() }
            .onSuccess { emit(success(it.data)) }
            .onFailure { emit(failed(it)) }
    }

    override fun getUser(id: Long): Flow<LoadState<User>> {
        TODO("Not yet implemented")
    }
}