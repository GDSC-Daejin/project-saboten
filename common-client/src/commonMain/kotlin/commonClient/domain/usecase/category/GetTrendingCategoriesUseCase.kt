package commonClient.domain.usecase.category

import commonClient.domain.repository.CategoryRepository
import org.koin.core.annotation.Single

@Single
class GetTrendingCategoriesUseCase(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke() = categoryRepository.getCategories()

}