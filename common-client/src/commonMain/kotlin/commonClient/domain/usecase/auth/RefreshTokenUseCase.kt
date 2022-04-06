package commonClient.domain.usecase.auth

import commonClient.data.LoadState
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.utils.AuthTokenManager
import kotlinx.coroutines.flow.flow

@Singleton
class RefreshTokenUseCase @Inject constructor(
    private val authTokenManager: AuthTokenManager
) {

    suspend operator fun invoke() = flow<LoadState<Unit>> {
        authTokenManager.refreshTokenIfNeeded()
            .onFailure {
                emit(LoadState.Failed(it))
            }.onSuccess {
                emit(LoadState.Success(Unit))
            }
    }

}