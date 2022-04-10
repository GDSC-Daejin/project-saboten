package commonClient.domain.usecase.category

import common.model.reseponse.category.CategoryResponse
import commonClient.data.LoadState
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.CategoryRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Singleton
class GetCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    //    operator fun invoke() = categoryRepository.getCategories()
    operator fun invoke(): Flow<LoadState<List<CategoryResponse>>> {
        return flow {
            emit(LoadState.loading())
            delay(2000)
            emit(LoadState.success(
                (0..7L).map {
                    CategoryResponse(
                        it,
                        "Category $it",
                        "https://raw.githubusercontent.com/GDSC-Daejin/project-saboten-iconpack/master/ic_favorite.svg"
                    )
                }
            ))
        }
    }

}