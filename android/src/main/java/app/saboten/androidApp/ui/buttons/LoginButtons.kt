package app.saboten.androidApp.ui.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.buttons.FilledButton
import app.saboten.androidUi.image.sabotenlogo.Google
import app.saboten.androidUi.image.sabotenlogo.SabotenIcons

@Composable
internal fun GoogleLoginButton(
    onClick: () -> Unit
) {

    FilledButton(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.onBackground.copy(0.05f),
        icon = SabotenIcons.Google,
        text = "Google로 로그인",
    )

}