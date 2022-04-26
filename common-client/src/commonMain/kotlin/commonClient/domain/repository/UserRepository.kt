package commonClient.domain.repository

import common.model.request.user.UserUpdateRequest
import common.model.reseponse.user.UserInfoResponse
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getMe(): Flow<UserInfoResponse>

    fun observeMe() : Flow<UserInfoResponse?>

    fun getUser(id: Long): Flow<UserInfoResponse>

    fun updateUserInfo(userUpdateRequest: UserUpdateRequest) : Flow<UserInfoResponse>

}