package commonClient.domain.repository

import common.model.reseponse.category.CategoryResponse
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<LoadState<List<CategoryResponse>>>

    fun getCategory(id: Long): Flow<LoadState<CategoryResponse>>

}