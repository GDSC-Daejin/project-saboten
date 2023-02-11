package app.saboten.androidApp.ui.dialog

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.buttons.GoogleLoginButton
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

            }

            Spacer(modifier = Modifier.height(20.dp))

            Spacer(modifier = Modifier.navigationBarsPadding())

        }
    }
}