@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package app.saboten.commonClient.domain.usecase.post

import app.saboten.commonClient.domain.repository.PostRepository
import com.chrynan.inject.Inject
import com.chrynan.inject.Singleton

@Singleton
class GetPosts @Inject constructor(private val postRepository: PostRepository) {

    operator fun invoke() = postRepository.getPosts()

}