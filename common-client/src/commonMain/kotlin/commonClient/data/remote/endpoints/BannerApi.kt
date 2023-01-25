package commonClient.data.remote.endpoints

import common.model.reseponse.ApiResponse
import common.model.reseponse.banner.BannerResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import org.koin.core.annotation.Single

@Single
class BannerApi : Api {
    override val prefixUrl: String
        get() = "/api/v1/banners"

    suspend fun getBanner() : ApiResponse<List<BannerResponse>> = responseGet("/")

}