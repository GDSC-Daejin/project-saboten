package app.saboten.androidUi.provider

import androidx.compose.runtime.Composable

@Composable
fun CommonProvider(
    content: @Composable () -> Unit
) {
    content()
}