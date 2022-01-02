package commonClient.data.repository

import common.model.User
import commonClient.data.LoadState
import commonClient.data.LoadState.Companion.loading
import commonClient.data.LoadState.Companion.success
import commonClient.domain.repository.UserRepository
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