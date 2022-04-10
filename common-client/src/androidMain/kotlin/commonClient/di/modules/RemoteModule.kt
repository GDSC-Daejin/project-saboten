package commonClient.di.modules

import com.russhwolf.settings.Settings
import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.remote.endpoints.*
import commonClient.data.repository.AppThemeSettingsRepositoryImp
import commonClient.data.repository.CategoryRepositoryImp
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.AppThemeSettingsRepository
import commonClient.domain.repository.CategoryRepository
import commonClient.domain.repository.UserRepository
import commonClient.utils.AuthTokenManager
import commonClient.utils.ClientProperties
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.cio.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {

        @Provides
        @Singleton
        fun provideHttpClient(
            properties: ClientProperties,
            @Named("encrypted") settings: Settings
        ) = SabotenApiHttpClient(
            CIO,
            settings.getStringOrNull(AuthTokenManager.KEY_ACCESS_TOKEN),
            properties
        )

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