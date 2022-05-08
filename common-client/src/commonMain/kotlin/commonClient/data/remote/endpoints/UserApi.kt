package commonClient.data.remote.endpoints

import common.model.request.user.UserUpdateRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.user.UserInfoResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import commonClient.data.remote.responsePatch
import commonClient.di.Inject
import commonClient.di.Singleton
import io.ktor.client.*
import io.ktor.client.request.*

interface UserApi : Api {

    override val prefixUrl: String get() = "/api/v1/users"

    suspend fun getMe(): ApiResponse<UserInfoResponse>

    suspend fun getUser(id: Long): ApiResponse<UserInfoResponse>

    suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest): ApiResponse<UserInfoResponse>

}

@Singleton
class UserApiImp @Inject constructor(
    override val httpClient: HttpClient
) : UserApi {

    override suspend fun getMe() = responseGet<UserInfoResponse>("me")

    override suspend fun getUser(id: Long) = responseGet<UserInfoResponse>(id)

    override suspend fun updateUserInfo(userUpdateRequest: UserUpdateRequest) = responsePatch<UserInfoResponse> {
        setBody(userUpdateRequest)
    }

}