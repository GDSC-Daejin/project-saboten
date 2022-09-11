package app.saboten.androidApp.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.buttons.EmailLoginButton
import app.saboten.androidApp.ui.buttons.GoogleLoginButton
import app.saboten.androidApp.ui.buttons.KakaoLoginButton
import app.saboten.androidUi.buttons.FilledButton
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.surfaceOver
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun LoginScreen(
    navigator: DestinationsNavigator
) {

    BasicScaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {

                GoogleLoginButton {

                }

                Spacer(modifier = Modifier.height(10.dp))

                KakaoLoginButton {

                }

                Spacer(modifier = Modifier.height(10.dp))

                EmailLoginButton {

                }

                Spacer(modifier = Modifier.height(10.dp))


                FilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "가입 없이 둘러보기",
                    backgroundColor = MaterialTheme.colors.surfaceOver,
                    onClick = { navigator.popBackStack() }
                )

            }
        }

    }


}