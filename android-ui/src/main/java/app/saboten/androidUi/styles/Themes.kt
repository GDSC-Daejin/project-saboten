package app.saboten.androidUi.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MainTheme(
    isDarkTheme : Boolean = isSystemInDarkTheme(),
    content : @Composable () -> Unit
) {
    MaterialTheme(
        if (isDarkTheme) darkColors else lightColors,
        shapes = shape,
        typography = typo,
        content = { content() }
    )
}