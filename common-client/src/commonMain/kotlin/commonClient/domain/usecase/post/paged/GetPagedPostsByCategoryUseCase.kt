package commonClient.domain.usecase.post.paged

import commonClient.domain.entity.PagingRequest
import commonClient.domain.repository.PostRepository
import org.koin.core.annotation.Single

@Single
class GetPagedPostsByCategoryUseCase(
    private val postRepository: PostRepository,
) {

    suspend operator fun invoke(categoryId: Long?, pagingRequest: PagingRequest) =
        postRepository.getPosts(categoryId, pagingRequest)

}
