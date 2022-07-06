package app.saboten.androidApp

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import app.saboten.androidApp.extensions.extract
import app.saboten.androidApp.ui.providers.ProvideMeInfo
import app.saboten.androidApp.ui.screens.AppScreen
import app.saboten.androidUi.styles.MainTheme
import coil.Coil
import coil.ImageLoader
import coil.decode.SvgDecoder
import commonClient.data.isLoading
import commonClient.domain.entity.settings.AppTheme
import commonClient.presentation.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    private val appViewModel by viewModels<AppViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setupSplashScreen(splashScreen)
        setupCoilImageLoader()
        setupUi()

        appViewModel.state
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
            val (state) = appViewModel.extract()
            MainTheme(
                isDarkTheme = when (state.appTheme) {
                    AppTheme.DARK -> true
                    AppTheme.LIGHT -> false
                    else -> isSystemInDarkTheme()
                }
            ) {
                ProvideMeInfo(state.me) {
                    AppScreen(appViewModel)
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
                    return if (!appViewModel.state.value.appLoadingState.isLoading()) {
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