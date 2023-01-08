package app.saboten.androidApp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import app.saboten.androidApp.ui.providers.ProvideMeInfo
import app.saboten.androidApp.ui.screens.AppScreen
import app.saboten.androidUi.styles.MainTheme
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import commonClient.data.isLoading
import commonClient.domain.entity.settings.AppTheme
import commonClient.presentation.GlobalAppViewModel
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
            MainTheme(
//                isDarkTheme = when (state.appTheme) {
//                    AppTheme.DARK -> true
//                    AppTheme.LIGHT -> false
//                    else -> isSystemInDarkTheme()
//                }
            false
            ) {
                ProvideMeInfo(state.meState) {
                    AppScreen(globalAppViewModel)
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
            with(ObjectAnimator.ofFloat(splashScreenView.view, View.ALPHA, 1f, 0f)) {
                duration = 450L
                doOnEnd { splashScreenView.remove() }
                start()
            }
        }

        /*  */
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    return if (!globalAppViewModel.container.stateFlow.value.appLoadingState.isLoading()) {
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        false
                    }
                }
            }
        )

    }

}