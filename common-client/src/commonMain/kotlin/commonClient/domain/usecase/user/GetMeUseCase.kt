package commonClient.domain.usecase.user

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.UserRepository

@Singleton
class GetMeUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    operator fun invoke()  = userRepository.getMe()

}