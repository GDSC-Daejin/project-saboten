package commonClient.utils

import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings

actual class EncryptedSettingsHolder {
    actual val settings: Settings
        get() = KeychainSettings(service = "preferences.auth_token")
}