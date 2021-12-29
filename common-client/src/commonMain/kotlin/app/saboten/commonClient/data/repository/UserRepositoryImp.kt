package app.saboten.commonClient.data.repository

import app.saboten.common.entities.User
import app.saboten.commonClient.data.LoadState
import app.saboten.commonClient.data.LoadState.Companion.loading
import app.saboten.commonClient.data.LoadState.Companion.success
import app.saboten.commonClient.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepositoryImp : UserRepository {
    // TODO 진짜 코드로 교체해야함
    override fun getMe() = flow {
        emit(loading())
        kotlinx.coroutines.delay(1000)
        emit(success(User(1, "Harry", "")))
    }

    override fun getUser(id: Long): Flow<LoadState<User>> {
        TODO("Not yet implemented")
    }
}