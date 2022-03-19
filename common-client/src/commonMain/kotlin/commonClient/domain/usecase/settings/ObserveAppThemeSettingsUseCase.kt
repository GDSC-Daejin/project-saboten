package commonClient.domain.usecase.settings

import commonClient.di.Inject
import commonClient.di.Singleton
import commonClient.domain.repository.AppThemeSettingsRepository

@Singleton
class ObserveAppThemeSettingsUseCase @Inject constructor(
    appThemeSettingsRepository: AppThemeSettingsRepository
) {
    private val themeFlow = appThemeSettingsRepository.getTheme()
    operator fun invoke() = themeFlow
}