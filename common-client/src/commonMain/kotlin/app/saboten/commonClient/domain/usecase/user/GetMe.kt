@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package app.saboten.commonClient.domain.usecase.user

import app.saboten.commonClient.domain.repository.UserRepository
import com.chrynan.inject.Inject
import com.chrynan.inject.Singleton

@Singleton
class GetMe @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke()  = userRepository.getMe()

}