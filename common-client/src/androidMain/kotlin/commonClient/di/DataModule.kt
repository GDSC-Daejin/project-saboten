package commonClient.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.datastore.DataStoreSettings
import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.remote.UserApi
import commonClient.data.remote.UserApiImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.cio.*
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("datastore")

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideHttpClient() = SabotenApiHttpClient(CIO)

        @Provides
        @Singleton
        fun provideAndroidSettings(@ApplicationContext context: Context): SuspendSettings =
            DataStoreSettings(context.dataStore)

    }

    @get:[Binds]
    val UserApiImp.userApi: UserApi

}