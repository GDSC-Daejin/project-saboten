package commonClient.domain.usecase.user

import common.model.request.user.UserUpdateRequest
import commonClient.domain.repository.UserRepository
import org.koin.core.annotation.Single

@Single
class UpdateUserInfoUseCase(
    private val userRepository: UserRepository
)  {

    operator fun invoke(userUpdateRequest: UserUpdateRequest) = userRepository.updateUserInfo(userUpdateRequest)

}