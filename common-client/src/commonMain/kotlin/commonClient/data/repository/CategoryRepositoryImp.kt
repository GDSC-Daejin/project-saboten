package commonClient.data.repository

import common.model.reseponse.category.CategoryResponse
import commonClient.data.LoadState
import commonClient.data.remote.endpoints.CategoryApi
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.flow

@Singleton
class CategoryRepositoryImp @Inject constructor(
    private val categoryApi: CategoryApi
) : CategoryRepository {

    private var memoryCachedCategories: List<CategoryResponse>? = null

    override fun getCategories() = flow {
        if (memoryCachedCategories.isNullOrEmpty()) {
            val response = categoryApi.getCategories()
            emit(requireNotNull(response.data))
        } else {
            emit(requireNotNull(memoryCachedCategories))
        }
    }

    override fun getCategory(id: Long) = flow {
        if (memoryCachedCategories.isNullOrEmpty()) {
            val response = categoryApi.getCategory(id)
            emit(requireNotNull(response.data))
        } else {
            val category = memoryCachedCategories!!.find { it.id == id }
            if (category != null) {
                emit(category)
            } else {
                throw Exception("Category not found")
            }
        }
    }

}