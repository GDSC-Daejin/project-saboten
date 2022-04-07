package commonClient.data.repository

import common.model.reseponse.category.Category
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

    private var memoryCachedCategories: List<Category>? = null

    override fun getCategories() = flow<LoadState<List<Category>>> {
        if (memoryCachedCategories.isNullOrEmpty()) {
            emit(LoadState.loading())
            categoryApi
                .runCatching { getCategories() }
                .onFailure { emit(LoadState.failed(it, null)) }
                .onSuccess {
                    memoryCachedCategories = it.data
                    emit(LoadState.success(it.data))
                }
        } else {
            emit(LoadState.success(memoryCachedCategories!!))
        }
    }

    override fun getCategory(id: Long) = flow<LoadState<Category>> {
        if (memoryCachedCategories.isNullOrEmpty()) {
            emit(LoadState.loading())
            categoryApi
                .runCatching { getCategory(id) }
                .onFailure { emit(LoadState.failed(it, null)) }
                .onSuccess { emit(LoadState.success(it.data)) }
        } else {
            val category = memoryCachedCategories!!.find { it.id == id }
            if (category != null) {
                emit(LoadState.success(category))
            } else {
                emit(LoadState.failed(Exception("Category not found"), null))
            }
        }
    }

}