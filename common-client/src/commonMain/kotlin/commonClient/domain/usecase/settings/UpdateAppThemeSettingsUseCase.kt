package commonClient.domain.usecase.settings

import commonClient.domain.entity.settings.AppTheme
import commonClient.domain.repository.AppThemeSettingsRepository
import org.koin.core.annotation.Single

@Single
class UpdateAppThemeSettingsUseCase(
    private val appThemeSettingsRepository: AppThemeSettingsRepository
) {
    suspend operator fun invoke(appTheme: AppTheme) {
        appThemeSettingsRepository.updateTheme(appTheme)
    }
}