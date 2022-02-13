package app.saboten.androidUi.provider

import androidx.compose.runtime.Composable
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun CommonProvider(
    content: @Composable () -> Unit
) {
    ProvideWindowInsets {
        content()
    }
}