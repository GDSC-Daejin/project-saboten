package commonClient.domain.usecase.user

import commonClient.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class ObserveMeUseCase(private val userRepository: UserRepository) {

    operator fun invoke() = userRepository.observeMe()

}