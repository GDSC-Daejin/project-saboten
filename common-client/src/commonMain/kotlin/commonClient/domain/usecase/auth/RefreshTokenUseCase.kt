package commonClient.domain.usecase.auth

import commonClient.domain.repository.AuthRepository
import org.koin.core.annotation.Single

@Single
class RefreshTokenUseCase(
    private val authRepository: AuthRepository
) {

    operator fun invoke(forceRefresh : Boolean) = authRepository.refreshToken(forceRefresh)

}