@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package app.saboten.commonCllient.domain.usecase.post

import app.saboten.commonCllient.domain.repository.PostRepository
import com.chrynan.inject.Inject
import com.chrynan.inject.Singleton

@Singleton
class GetPosts @Inject constructor(private val postRepository: PostRepository) {

    operator fun invoke() = postRepository.getPosts()

}