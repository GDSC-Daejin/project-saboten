package app.saboten.commonClient.domain.repository

import app.saboten.common.entities.User
import app.saboten.commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getMe(): Flow<LoadState<User>>

    fun getUser(id: Long): Flow<LoadState<User>>

}