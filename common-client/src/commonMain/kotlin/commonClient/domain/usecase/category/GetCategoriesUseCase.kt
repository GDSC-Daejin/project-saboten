package commonClient.domain.usecase.category

import commonClient.domain.repository.CategoryRepository
import org.koin.core.annotation.Single

@Single
class GetCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {

    suspend operator fun invoke() = categoryRepository.getCategories()

}