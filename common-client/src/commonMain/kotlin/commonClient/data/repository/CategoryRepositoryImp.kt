package commonClient.data.repository

import commonClient.data.remote.endpoints.CategoryApi
import commonClient.domain.entity.post.Category
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.flow
import org.koin.core.annotation.Single

@Single(binds = [CategoryRepository::class])
class CategoryRepositoryImp(
    private val categoryApi: CategoryApi,
) : CategoryRepository {

    private var memoryCachedCategories: List<Category>? = null

    override suspend fun getCategories(): List<Category> {
        if (memoryCachedCategories.isNullOrEmpty()) {
            val response = categoryApi.getCategories()
            memoryCachedCategories = response.data?.map { it.toDomain() }
        }

        return mutableListOf(Category(-1, "전체", "")) + requireNotNull(memoryCachedCategories)
    }

    override fun getCategory(id: Long) = flow {
        if (memoryCachedCategories.isNullOrEmpty()) {
            val response = categoryApi.getCategory(id)
            emit(requireNotNull(response.data?.toDomain()))
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