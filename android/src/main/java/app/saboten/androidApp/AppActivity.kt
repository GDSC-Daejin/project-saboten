package app.saboten.androidApp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import app.saboten.androidApp.ui.providers.ProvideMeInfo
import app.saboten.androidApp.ui.screens.AppScreen
import app.saboten.androidApp.ui.screens.SplashScreen
import app.saboten.androidUi.styles.MainTheme
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import commonClient.data.isLoading
import commonClient.domain.entity.settings.AppTheme
import commonClient.presentation.GlobalAppViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.orbitmvi.orbit.compose.collectAsState

class AppActivity : AppCompatActivity() {

    private val globalAppViewModel by viewModel<GlobalAppViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setupSplashScreen(splashScreen)
        setupCoilImageLoader()
        setupUi()

        globalAppViewModel.container.stateFlow
            .onEach {
                AppCompatDelegate.setDefaultNightMode(
                    when (it.appTheme) {
                        AppTheme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        AppTheme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                        AppTheme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                    }
                )
            }.launchIn(lifecycleScope)
    }

    private fun setupUi() {
        setContent {
            val state by globalAppViewModel.collectAsState()

            var showAppScreen by remember {
                mutableStateOf(false)
            }

            LaunchedEffect(true) {
                delay(2000)
                showAppScreen = true
            }

            MainTheme(
//                isDarkTheme = when (state.appTheme) {
//                    AppTheme.DARK -> true
//                    AppTheme.LIGHT -> false
//                    else -> isSystemInDarkTheme()
//                }
            false
            ) {
                ProvideMeInfo(state.meState) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        SplashScreen()
                        if (showAppScreen) AppScreen(globalAppViewModel)
                    }
                }
            }
        }
    }

    private fun setupCoilImageLoader() {
        Coil.setImageLoader(
            ImageLoader.Builder(this)
                .components { add(SvgDecoder.Factory()) }
                .build()
        )
    }

    private fun setupSplashScreen(splashScreen: SplashScreen) {

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.remove()
        }

        splashScreen.setKeepOnScreenCondition { false }
    }

}