package app.saboten.androidUiSamples

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import app.saboten.androidUi.styles.MainTheme
import app.saboten.androidUiSamples.ThemeState.Dark
import app.saboten.androidUiSamples.ThemeState.Light
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class UiSamplesActivity : AppCompatActivity() {

    private val viewModel by viewModels<UiSamplesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val themeState by viewModel.themeStateFlow.collectAsState()

            val systemUiController = rememberSystemUiController()

            LaunchedEffect(themeState) {
                systemUiController.systemBarsDarkContentEnabled = themeState == Light
            }

            MainTheme(isDarkTheme = themeState == Dark) {
                UiSamplesApp(viewModel)
            }
        }

    }

}