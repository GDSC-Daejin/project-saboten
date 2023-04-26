package commonClient.data.remote.endpoints

import common.model.request.user.UserUpdateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.user.UserInfoResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import commonClient.data.remote.responsePatch
import commonClient.utils.AuthTokenManager
import io.ktor.client.request.setBody
import org.koin.core.annotation.Single

interface UserApi : Api {

    override val prefixUrl: String get() = "/api/v1/userInfo"

    suspend fun getMe(): ApiResponse<UserInfoResponse>

    suspend fun getUser(id: Long): ApiResponse<UserInfoResponse>

    suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): ApiResponse<UserInfoResponse>

}

@Single(binds = [UserApi::class])
class UserApiImp(override val authTokenManager: AuthTokenManager) : UserApi {

    override suspend fun getMe() = responseGet<UserInfoResponse>("/")

    override suspend fun getUser(id: Long) = responseGet<UserInfoResponse>("/$id")

    override suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest) = responsePatch<UserInfoResponse> {
        setBody(userUpdateRequest)
    }

}