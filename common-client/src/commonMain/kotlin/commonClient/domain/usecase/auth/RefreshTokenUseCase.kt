package commonClient.domain.usecase.auth

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.AuthRepository

@Singleton
class RefreshTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    operator fun invoke(forceRefresh : Boolean) = authRepository.refreshToken(forceRefresh)

}