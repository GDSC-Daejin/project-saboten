package commonClient.domain.usecase.user

import commonClient.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class GetUserUseCase(
    private val userRepository: UserRepository
) {

    operator fun invoke(userId: Long) = userRepository.getUser(userId)

}