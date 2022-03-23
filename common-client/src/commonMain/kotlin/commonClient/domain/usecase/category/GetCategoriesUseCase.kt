package commonClient.domain.usecase.category

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.CategoryRepository

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    operator fun invoke() = categoryRepository.getCategories()

}