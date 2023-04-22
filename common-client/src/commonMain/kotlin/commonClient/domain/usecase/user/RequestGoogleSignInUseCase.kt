package commonClient.domain.usecase.user

import commonClient.domain.repository.AuthRepository
import commonClient.domain.repository.UserRepository
import commonClient.logger.ClientLogger
import org.koin.core.annotation.Single

@Single
class RequestGoogleSignInUseCase(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(idToken: String) {
        ClientLogger.d("RequestGoogleSignInUseCase: $idToken")
        authRepository.requestGoogleLogin(idToken)
        userRepository.getMe()
    }

}