package app.saboten.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import app.saboten.androidApp.ui.screens.AppScreen
import app.saboten.androidUi.styles.MainTheme
import com.google.accompanist.insets.ProvideWindowInsets

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MainTheme {
                ProvideWindowInsets {
                    AppScreen()
                }
            }
        }
    }

}