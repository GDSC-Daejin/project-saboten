@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package commonClient.domain.usecase.post

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.PostRepository

@Singleton
class GetPosts @Inject constructor(private val postRepository: PostRepository) {

    operator fun invoke() = postRepository.getPosts()

}