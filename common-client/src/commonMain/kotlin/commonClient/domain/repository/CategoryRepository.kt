package commonClient.domain.repository

import commonClient.domain.entity.post.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<Category>>

    fun getCategory(id: Long): Flow<Category>

}