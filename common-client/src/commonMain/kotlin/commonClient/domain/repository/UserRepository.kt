package commonClient.domain.repository

import common.model.User
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getMe(): Flow<LoadState<User>>

    fun getUser(id: Long): Flow<LoadState<User>>

}