@file:OptIn(ExperimentalSettingsApi::class)

package commonClient.data.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.entity.settings.AppTheme
import commonClient.domain.repository.AppThemeSettingsRepository
import commonClient.logger.ClientLogger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull

@Singleton
class AppThemeSettingsRepositoryImp @Inject constructor(
    private val settings: SuspendSettings
) : AppThemeSettingsRepository {

    private val appThemeFlow = MutableStateFlow<AppTheme?>(null)

    override fun getTheme() = appThemeFlow.mapNotNull {
        val theme = it ?: when (settings.getStringOrNull(KEY_APP_THEME)) {
            AppTheme.LIGHT.value -> AppTheme.LIGHT
            AppTheme.DARK.value -> AppTheme.DARK
            AppTheme.SYSTEM.value -> AppTheme.SYSTEM
            else -> AppTheme.SYSTEM
        }
        ClientLogger.d("AppThemeSettingsRepositoryImp | theme: $theme")
        theme
    }

    override suspend fun updateTheme(appTheme: AppTheme) {
        ClientLogger.d("AppThemeSettingsRepositoryImp | theme updated: $appTheme")
        settings.putString(KEY_APP_THEME, appTheme.value)
        appThemeFlow.emit(appTheme)
    }

    companion object {
        private const val KEY_APP_THEME = "app_theme"
    }
}