package app.saboten.androidApp.ui.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.buttons.FilledButton

@Composable
internal fun GoogleLoginButton(
    onClick: () -> Unit
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground.copy(0.1f))
    ) {
//        Icon()
        Spacer(modifier = Modifier.width(10.dp))
        Text("Login with Google")
    }

}

@Composable
internal fun KakaoLoginButton(
    onClick: () -> Unit
) {

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
    ) {
//        Icon()
        Spacer(modifier = Modifier.width(10.dp))
        Text("Login with Kakao")
    }

}

@Composable
internal fun EmailLoginButton(
    onClick: () -> Unit
) {

    FilledButton(
        onClick = onClick,
        text = "Login with Email",
        icon = Icons.Rounded.Email
    )

}