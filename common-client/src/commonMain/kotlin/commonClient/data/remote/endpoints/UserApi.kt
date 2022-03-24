package commonClient.data.remote.endpoints

import common.model.request.user.UserUpdateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.user.UserInfo
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import commonClient.data.remote.responsePatch
import commonClient.di.Inject
import commonClient.di.Singleton
import io.ktor.client.*
import io.ktor.client.request.*

interface UserApi : Api {

    override val prefixUrl: String get() = "/api/v1/users"

    suspend fun getMe(): ApiResponse<UserInfo>

    suspend fun getUser(id: Long): ApiResponse<UserInfo>

    suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): ApiResponse<UserInfo>

}

@Singleton
class UserApiImp @Inject constructor(override val httpClient: HttpClient) : UserApi {

    override suspend fun getMe() = responseGet<UserInfo>("me")

    override suspend fun getUser(id: Long) = responseGet<UserInfo>(id)

    override suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest) = responsePatch<UserInfo> {
        setBody(userUpdateRequest)
    }

}