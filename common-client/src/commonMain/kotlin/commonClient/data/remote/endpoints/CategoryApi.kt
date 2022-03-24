package commonClient.data.remote.endpoints

import common.model.reseponse.ApiResponse
import common.model.reseponse.category.Category
import commonClient.data.remote.Api
import commonClient.data.remote.responseGet
import commonClient.di.Inject
import commonClient.di.Singleton
import io.ktor.client.*

interface CategoryApi : Api {

    override val prefixUrl: String get() = "/api/v1/category"

    suspend fun getCategories(): ApiResponse<List<Category>>

    suspend fun getCategory(id: Long): ApiResponse<Category>

}

@Singleton
class CategoryApiImp @Inject constructor(override val httpClient: HttpClient) : CategoryApi {

    override suspend fun getCategories() = responseGet<List<Category>>()

    override suspend fun getCategory(id: Long) = responseGet<Category>(id)

}

