package app.saboten.commonCllient.domain.repository

import app.saboten.common.entities.ApiResponse
import app.saboten.common.entities.Post
import app.saboten.common.entities.User
import app.saboten.commonCllient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getMe(): Flow<LoadState<User>>

    fun getUser(id: Long): Flow<LoadState<User>>

}