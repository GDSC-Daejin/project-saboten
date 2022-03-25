package commonClient.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.russhwolf.settings.coroutines.SuspendSettings
import com.russhwolf.settings.datastore.DataStoreSettings
import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.remote.endpoints.*
import commonClient.data.repository.AppThemeSettingsRepositoryImp
import commonClient.data.repository.CategoryRepositoryImp
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.AppThemeSettingsRepository
import commonClient.domain.repository.CategoryRepository
import commonClient.domain.repository.UserRepository
import commonClient.utils.ClientProperties
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
        fun provideHttpClient(properties: ClientProperties) = SabotenApiHttpClient(CIO, properties)

        @Provides
        @Singleton
        fun provideAndroidSettings(@ApplicationContext context: Context): SuspendSettings =
            DataStoreSettings(context.dataStore)

    }

    /* API */
    @get:[Binds]
    val UserApiImp.userApi: UserApi

    @get:[Binds]
    val PostApiImp.postApi: PostApi

    @get:[Binds]
    val AuthApiImp.authApi: AuthApi

    @get:[Binds]
    val CategoryApiImp.categoryApi: CategoryApi

    /* Repositories */
    @get:[Binds]
    val UserRepositoryImp.userRepository: UserRepository

    @get:[Binds]
    val CategoryRepositoryImp.categoryRepository: CategoryRepository

    @get:[Binds]
    val AppThemeSettingsRepositoryImp.appThemeSettingsRepository: AppThemeSettingsRepository

}