package commonClient.domain.repository

import common.model.request.user.UserUpdateRequest
import commonClient.domain.entity.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getMe(): UserInfo

    fun observeMe() : Flow<UserInfo?>

    suspend fun getUser(id: Long): UserInfo

    suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest) : UserInfo

}