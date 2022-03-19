package commonClient.domain.usecase.user

import common.model.request.user.UserUpdateRequest
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.UserRepository

@Singleton
class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
)  {

    operator fun invoke(userUpdateRequest: UserUpdateRequest) = userRepository.updateUserInfo(userUpdateRequest)

}