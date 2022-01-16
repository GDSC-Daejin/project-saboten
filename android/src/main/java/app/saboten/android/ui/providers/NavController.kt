package app.saboten.android.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

val LocalAppNavController = staticCompositionLocalOf<NavController> {
    error("Not Provided!")
}

@Composable
fun ProvideNavController(
    navController: NavController,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalAppNavController provides navController,
        content = content
    )
}