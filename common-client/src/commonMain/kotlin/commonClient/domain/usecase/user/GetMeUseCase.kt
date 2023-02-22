package commonClient.domain.usecase.user

import commonClient.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class GetMeUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke()  = userRepository.getMe()

}