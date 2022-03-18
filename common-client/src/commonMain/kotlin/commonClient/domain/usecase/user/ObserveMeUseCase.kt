package commonClient.domain.usecase.user

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.UserRepository

@Singleton
class ObserveMeUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke() = userRepository.observeMe()

}