package app.saboten.androidUiSamples

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.*
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import app.saboten.androidUi.provider.CommonProvider
import app.saboten.androidUi.styles.MainTheme
import app.saboten.androidUiSamples.ThemeState.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.*

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