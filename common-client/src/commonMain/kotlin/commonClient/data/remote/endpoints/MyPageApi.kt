package commonClient.data.remote.endpoints

import common.model.reseponse.ApiResponse
import common.model.reseponse.post.PostCountResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import commonClient.data.remote.responsePost
import org.koin.core.annotation.Single

interface MyPageApi : Api {

    override val prefixUrl: String get() = "/api/v1/mypage"

    suspend fun getMyPageCounts(): ApiResponse<PostCountResponse>

}

@Single(binds = [MyPageApi::class])
class MyPageApiImp : MyPageApi {

    override suspend fun getMyPageCounts(): ApiResponse<PostCountResponse> {
        return responseGet("")
    }
}