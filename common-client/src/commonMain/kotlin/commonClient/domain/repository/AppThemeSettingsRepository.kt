package commonClient.domain.repository

import commonClient.domain.entity.AppTheme
import kotlinx.coroutines.flow.Flow

interface AppThemeSettingsRepository {

    fun getTheme() : Flow<AppTheme>

    suspend fun updateTheme(appTheme: AppTheme)

}