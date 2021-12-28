package app.saboten.android.ui.styles

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MainTheme(
//    isDarkMode : Boolean = isSystemInDarkTheme(),
    isDarkMode : Boolean = false,
    content : @Composable () -> Unit
) {
    MaterialTheme(
        if (isDarkMode) darkColors else lightColors,
        content = content
    )
}