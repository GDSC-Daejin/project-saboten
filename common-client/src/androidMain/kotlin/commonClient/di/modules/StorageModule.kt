package commonClient.di.modules

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.datastore.DataStoreSettings
import commonClient.utils.EncryptedSettingsHolder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


private val Context.dataStore by preferencesDataStore("datastore")

@Module
@InstallIn(SingletonComponent::class)
class StorageModule {

    @Provides
    @Singleton
    fun provideAndroidSettings(@ApplicationContext context: Context): SuspendSettings =
        DataStoreSettings(context.dataStore)

    @Provides
    @Singleton
    @Named("encrypted")
    fun provideEncryptedSettings(@ApplicationContext context: Context): Settings =
        EncryptedSettingsHolder.newInstance(context).settings

}