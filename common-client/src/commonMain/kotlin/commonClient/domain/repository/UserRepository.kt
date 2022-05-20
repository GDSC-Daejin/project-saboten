package commonClient.domain.repository

import common.model.request.user.UserUpdateRequest
import commonClient.domain.entity.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getMe(): Flow<UserInfo>

    fun observeMe() : Flow<UserInfo?>

    fun getUser(id: Long): Flow<UserInfo>

    fun updateUserInfo(userUpdateRequest: UserUpdateRequest) : Flow<UserInfo>

}