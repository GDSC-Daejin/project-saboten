package app.saboten.androidApp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import app.saboten.androidApp.extensions.extract
import app.saboten.androidApp.ui.providers.ProvideMeInfo
import app.saboten.androidApp.ui.screens.AppScreen
import app.saboten.androidUi.styles.MainTheme
import commonClient.domain.entity.AppTheme
import commonClient.logger.ClientLogger
import commonClient.presentation.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        appViewModel.state
            .onEach {
                ClientLogger.d("AppActivity | state: $it")
                AppCompatDelegate.setDefaultNightMode(
                    when (it.appTheme) {
                        AppTheme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                        AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    }
                )
            }.launchIn(lifecycleScope)

        setContent {
            val (state) = appViewModel.extract()
            MainTheme(
                isDarkTheme = when (state.appTheme) {
                    AppTheme.DARK -> true
                    AppTheme.LIGHT -> false
                    else -> isSystemInDarkTheme()
                }
            ) {
                ProvideMeInfo(state.me) {
                    AppScreen()
                }
            }
        }
    }

}