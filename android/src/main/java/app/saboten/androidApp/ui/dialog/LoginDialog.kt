package app.saboten.androidApp.ui.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.buttons.EmailLoginButton
import app.saboten.androidApp.ui.buttons.GoogleLoginButton
import app.saboten.androidApp.ui.buttons.KakaoLoginButton
import app.saboten.androidUi.buttons.FilledButton
import app.saboten.androidUi.styles.surfaceOver
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun LoginDialog(
    navigator: DestinationsNavigator
) {
    LoginDialogContent()
}

@Composable
fun LoginDialogContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(372.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(modifier = Modifier.fillMaxWidth(0.8f)) {
            Spacer(modifier = Modifier.height(13.dp))

            Surface(
                modifier = Modifier
                    .size(width = 163.dp, height = 5.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
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
                onClick = {

                }
            )

        }
    }
}

@Composable
private fun LoginDialogButton(
    onClicked: () -> Unit,
    loginButtonText: String
) {
    FilledButton(
        onClick = onClicked,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(
            topStart = 10.dp,
            topEnd = 10.dp,
            bottomStart = 0.dp,
            bottomEnd = 10.dp
        ),
        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.05f),
        text = loginButtonText
    )
}