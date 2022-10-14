package commonClient.domain.usecase.settings

import commonClient.domain.repository.AppThemeSettingsRepository
import org.koin.core.annotation.Single

@Single
class ObserveAppThemeSettingsUseCase(
    appThemeSettingsRepository: AppThemeSettingsRepository
) {
    private val themeFlow = appThemeSettingsRepository.getTheme()
    operator fun invoke() = themeFlow
}