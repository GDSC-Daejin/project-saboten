package commonClient.domain.repository

import common.model.request.user.UserUpdateRequest
import common.model.reseponse.user.UserInfoResponse
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getMe(): Flow<LoadState<UserInfoResponse>>

    fun observeMe() : Flow<UserInfoResponse?>

    fun getUser(id: Long): Flow<LoadState<UserInfoResponse>>

    fun updateUserInfo(userUpdateRequest: UserUpdateRequest) : Flow<LoadState<UserInfoResponse>>

}