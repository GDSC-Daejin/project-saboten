package commonClient.domain.usecase.user

import commonClient.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class GetUserUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userId: Long) = userRepository.getUser(userId)

}