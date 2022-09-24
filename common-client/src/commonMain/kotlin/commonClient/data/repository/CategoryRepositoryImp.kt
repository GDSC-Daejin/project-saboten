package commonClient.data.repository

import commonClient.data.remote.endpoints.CategoryApi
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.entity.post.Category
import commonClient.domain.mapper.toDomain
import commonClient.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.flow

@Singleton
class CategoryRepositoryImp @Inject constructor(
    private val categoryApi: CategoryApi
) : CategoryRepository {

    //    private var memoryCachedCategories: List<Category>? = null
    private var memoryCachedCategories: List<Category>? = listOf(
        Category(0, "전체", ""),
        Category(0, "MBTI", ""),
        Category(0, "연애", ""),
        Category(0, "먹을거", ""),
        Category(0, "쇼핑", ""),
    )

    override fun getCategories() = flow {
        if (memoryCachedCategories.isNullOrEmpty()) {
            val response = categoryApi.getCategories()
            memoryCachedCategories = response.data?.map { it.toDomain() }
        }
        emit(requireNotNull(memoryCachedCategories))
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