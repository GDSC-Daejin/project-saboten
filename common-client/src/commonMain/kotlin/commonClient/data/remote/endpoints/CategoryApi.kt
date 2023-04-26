package commonClient.data.remote.endpoints

import common.model.reseponse.ApiResponse
import common.model.reseponse.category.CategoryResponse
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import commonClient.utils.AuthTokenManager
import org.koin.core.annotation.Single

interface CategoryApi : Api {

    override val prefixUrl: String get() = "/api/v1/category"

    suspend fun getCategories(): ApiResponse<List<CategoryResponse>>

    suspend fun getCategory(id: Long): ApiResponse<CategoryResponse>

}

@Single(binds = [CategoryApi::class])
class CategoryApiImp(override val authTokenManager: AuthTokenManager): CategoryApi {

    override suspend fun getCategories() = responseGet<List<CategoryResponse>>()

    override suspend fun getCategory(id: Long) = responseGet<CategoryResponse>(id)

}

