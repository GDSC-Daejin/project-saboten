package app.saboten.androidApp.ui.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.buttons.FilledButton

@Composable
internal fun GoogleLoginButton(
    onClick: () -> Unit
) {

    FilledButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        backgroundColor = Color.White,
        text = "Google로 로그인",
    )

}