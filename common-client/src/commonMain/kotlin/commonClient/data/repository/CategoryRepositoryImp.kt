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

    override fun getCategories() = flow<LoadState<List<Category>>> {
        emit(LoadState.loading())
        categoryApi
            .runCatching { getCategories() }
            .onFailure { emit(LoadState.failed(it, null)) }
            .onSuccess { emit(LoadState.success(it.data)) }
    }

    override fun getCategory(id: Long)= flow<LoadState<Category>> {
        emit(LoadState.loading())
        categoryApi
            .runCatching { getCategory(id) }
            .onFailure { emit(LoadState.failed(it, null)) }
            .onSuccess { emit(LoadState.success(it.data)) }
    }

}