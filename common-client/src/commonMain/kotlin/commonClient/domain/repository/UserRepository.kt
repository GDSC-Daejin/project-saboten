package commonClient.domain.repository

import common.model.request.user.UserUpdateRequest
import common.model.reseponse.user.UserInfo
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getMe(): Flow<UserInfo>

    fun observeMe() : Flow<UserInfo?>

    fun getUser(id: Long): Flow<UserInfo>

    fun updateUserInfo(userUpdateRequest: UserUpdateRequest) : Flow<UserInfo>

}