package commonClient.domain.usecase.post

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.PostRepository

@Singleton
class GetPagedPostByCategoryIdUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

//    operator fun invoke() = postRepository.getPost()

}