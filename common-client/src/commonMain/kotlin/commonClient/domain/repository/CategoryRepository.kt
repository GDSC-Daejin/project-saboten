package commonClient.domain.repository

import common.model.reseponse.category.Category
import commonClient.data.LoadState
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<LoadState<List<Category>>>

    fun getCategory(id: Long): Flow<LoadState<Category>>

}