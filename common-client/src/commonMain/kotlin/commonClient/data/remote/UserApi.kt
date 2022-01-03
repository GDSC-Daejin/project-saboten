package commonClient.data.remote

import com.chrynan.inject.Inject
import com.chrynan.inject.Singleton
import common.model.ApiResponse
import common.model.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface UserApi {

    suspend fun getMe() : ApiResponse<User>

}

@Singleton
class UserApiImp @Inject constructor(
    private val httpClient: HttpClient
) : UserApi {

    override suspend fun getMe(): ApiResponse<User> {

        val response =  httpClient.get("") {

        }

        return response.body()
    }



}