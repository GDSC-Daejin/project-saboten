package commonClient

import com.russhwolf.settings.MockSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import commonClient.data.repository.AppThemeSettingsRepositoryImp
import commonClient.domain.entity.AppTheme
import commonClient.domain.repository.AppThemeSettingsRepository
import commonClient.domain.usecase.settings.ObserveAppThemeSettingsUseCase
import commonClient.domain.usecase.settings.UpdateAppThemeSettingsUseCase
import commonClient.utils.JsName
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AppThemeSettingsTest {

    lateinit var observeAppThemeSettingsUseCase: ObserveAppThemeSettingsUseCase
    lateinit var updateAppThemeSettingsUseCase: UpdateAppThemeSettingsUseCase

    @BeforeTest
    fun setUp() {
        val mockSettings = MockSettings()

        val repository  : AppThemeSettingsRepository = AppThemeSettingsRepositoryImp(mockSettings.toSuspendSettings())

        observeAppThemeSettingsUseCase = ObserveAppThemeSettingsUseCase(repository)
        updateAppThemeSettingsUseCase = UpdateAppThemeSettingsUseCase(repository)
    }

    @Test
    @JsName("AppThemeChangeTest")
    fun `GIVEN AppTheme = "DARK" WHEN Default = "SYSTEM" THEN AppTheme = "DARK"`() = runTest {

        val defaultTheme = observeAppThemeSettingsUseCase().first()
        println("defaultTheme: $defaultTheme")
        assertEquals(defaultTheme, AppTheme.SYSTEM)

        // Given
        val appTheme = AppTheme.DARK

        // When
        updateAppThemeSettingsUseCase(appTheme)

        // Then
        val updatedTheme = observeAppThemeSettingsUseCase().first()
        println("updatedTheme: $updatedTheme")
        assertEquals(updatedTheme, AppTheme.DARK)
    }

}