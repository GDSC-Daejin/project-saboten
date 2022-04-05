package commonClient.utils

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings

actual class EncryptedSettingsHolder(context : Context) {

    actual val settings: Settings = AndroidSettings(
        EncryptedSharedPreferences.create(
            context,
            "preferences.auth_token",
            MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    )

    companion object {
        fun newInstance(context: Context) = EncryptedSettingsHolder(context)
    }

}