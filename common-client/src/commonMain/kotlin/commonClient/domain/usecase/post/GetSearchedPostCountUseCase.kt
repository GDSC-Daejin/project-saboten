package commonClient.domain.usecase.post

import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetSearchedPostCountUseCase(
    private val postRepository: PostRepository,
) {
    suspend operator fun invoke(searchText: String) = postRepository.getSearchedPostCount(searchText)
}