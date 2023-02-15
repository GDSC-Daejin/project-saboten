package commonClient.domain.usecase.user

import commonClient.domain.repository.AuthRepository
import commonClient.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class RequestGoogleSignInUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(idToken: String) {
        authRepository.requestGoogleLogin(idToken)
        userRepository.getMe()
    }

}