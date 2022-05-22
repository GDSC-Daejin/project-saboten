package commonClient.domain.usecase.settings

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.entity.settings.AppTheme
import commonClient.domain.repository.AppThemeSettingsRepository

@Singleton
class UpdateAppThemeSettingsUseCase @Inject constructor(
    private val appThemeSettingsRepository: AppThemeSettingsRepository
) {
    suspend operator fun invoke(appTheme: AppTheme) {
        appThemeSettingsRepository.updateTheme(appTheme)
    }
}