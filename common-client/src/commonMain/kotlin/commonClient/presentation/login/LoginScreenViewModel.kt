package commonClient.presentation.login

import commonClient.data.LoadState
import commonClient.domain.entity.post.Post
import commonClient.domain.usecase.user.RequestGoogleSignInUseCase
import commonClient.logger.ClientLogger
import commonClient.presentation.PlatformViewModel
import commonClient.presentation.container
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect

interface LoginScreenEffect {
    object SignInSuccess : LoginScreenEffect
    data class Toast(val message : String) : LoginScreenEffect
}

data class LoginScreenState(
    val post: LoadState<Post> = LoadState.idle(),
)

class LoginScreenViewModel(
    private val requestGoogleSignInUseCase: RequestGoogleSignInUseCase,
) : PlatformViewModel<LoginScreenState, LoginScreenEffect>() {

    override val container: Container<LoginScreenState, LoginScreenEffect> =
        container(LoginScreenState())

    fun requestGoogleSignIn(idToken: String) = intent {
        kotlin.runCatching {
            requestGoogleSignInUseCase(idToken)
        }.onFailure {
            showToast(it.message ?: "")
            ClientLogger.e(it)
        }.onSuccess {
            postSideEffect(LoginScreenEffect.SignInSuccess)
        }
    }

    fun showToast(message: String) = intent {
        postSideEffect(LoginScreenEffect.Toast(message))
    }

}