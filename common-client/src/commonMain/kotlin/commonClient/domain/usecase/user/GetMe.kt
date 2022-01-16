@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package commonClient.domain.usecase.user

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.UserRepository

@Singleton
class GetMe @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke()  = userRepository.getMe()

}