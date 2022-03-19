package app.saboten.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import app.saboten.androidApp.ui.providers.ProvideMeInfo
import app.saboten.androidApp.ui.screens.AppScreen
import app.saboten.androidUi.styles.MainTheme
import commonClient.extension.extract
import commonClient.presentation.AppViewModel

class AppActivity : ComponentActivity() {

    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val (state) = appViewModel.extract()
            MainTheme {
                ProvideMeInfo(state.me) {
                    AppScreen()
                }
            }
        }
    }

}