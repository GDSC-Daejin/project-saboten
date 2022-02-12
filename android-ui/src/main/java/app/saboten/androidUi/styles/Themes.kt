package app.saboten.androidUi.styles

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MainTheme(
    isDarkMode : Boolean = isSystemInDarkTheme(),
    content : @Composable () -> Unit
) {
    MaterialTheme(
        if (isDarkMode) darkColors else lightColors,
        shapes = shape,
        typography = typo,
        content = content
    )
}