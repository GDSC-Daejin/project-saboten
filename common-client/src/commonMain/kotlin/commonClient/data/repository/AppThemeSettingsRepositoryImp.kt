package commonClient.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import commonClient.domain.entity.settings.AppTheme
import commonClient.domain.repository.AppThemeSettingsRepository
import commonClient.logger.ClientLogger
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class AppThemeSettingsRepositoryImp constructor(
    private val settings: DataStore<Preferences>,
) : AppThemeSettingsRepository {

    override fun getTheme() = settings.data
        .map { it[KEY_APP_THEME] }
        .map {
            val theme = when (it) {
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
        settings.edit { it[KEY_APP_THEME] = appTheme.value }
    }

    companion object {
        private val KEY_APP_THEME = stringPreferencesKey("app_theme")
    }
}