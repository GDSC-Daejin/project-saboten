package app.saboten.androidApp.ui.dialog

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.buttons.GoogleLoginButton
import app.saboten.androidUi.utils.getActivity
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import commonClient.logger.ClientLogger
import commonClient.presentation.login.LoginScreenEffect
import commonClient.presentation.login.LoginScreenViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun LoginDialog(
    navigator: DestinationsNavigator,
) {
    LoginDialogContent(
        dismiss = { navigator.popBackStack() }
    )
}

@Composable
fun LoginDialogContent(
    dismiss: () -> Unit,
) {

    val context = LocalContext.current

    val viewModel = koinViewModel<LoginScreenViewModel>()

    viewModel.collectSideEffect {
        when (it) {
            is LoginScreenEffect.SignInSuccess -> dismiss()
            is LoginScreenEffect.Toast -> {
                context.getActivity()?.let { activity ->
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val coroutineScope = rememberCoroutineScope {
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            dismiss()
        }
    }

    val googleLoginManager = remember {
        GoogleLoginManager(context.getActivity()!!)
    }

    val requestSignIn = rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->

        ClientLogger.d("result: $result")
        if (result.resultCode == Activity.RESULT_OK) {
            ClientLogger.d("Success")
            val intent = result.data
            if (intent != null) {
                val credential = Identity.getSignInClient(context.getActivity()!!).getSignInCredentialFromIntent(intent)
                val googleIdToken = credential.googleIdToken
                ClientLogger.d("Success $googleIdToken")
                if (googleIdToken != null) {
                    viewModel.requestGoogleSignIn(googleIdToken)
                }
            } else {
                viewModel.showToast("로그인에 실패했습니다.")
            }
        } else {
            viewModel.showToast("로그인에 실패했습니다.")
        }
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            Spacer(modifier = Modifier.height(13.dp))

            Surface(
                modifier = Modifier
                    .size(width = 100.dp, height = 4.dp)
                    .align(Alignment.CenterHorizontally),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f)
            ) {}

            Spacer(modifier = Modifier.height(21.dp))

            Text(
                text = "로그인하기",
                style = MaterialTheme.typography.h6
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "로그인하시면 더 많은 기능 이용이 가능해요.",
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(25.dp))

            GoogleLoginButton {
                coroutineScope.launch {
                    val signInIntent = googleLoginManager.signInIntent()
                    requestSignIn.launch(
                        IntentSenderRequest.Builder(signInIntent)
                            .build()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.navigationBarsPadding())

        }
    }
}