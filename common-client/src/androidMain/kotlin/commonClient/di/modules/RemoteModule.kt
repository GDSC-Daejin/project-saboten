package commonClient.di.modules

import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.remote.endpoints.*
import commonClient.data.repository.AppThemeSettingsRepositoryImp
import commonClient.data.repository.AuthRepositoryImp
import commonClient.data.repository.CategoryRepositoryImp
import commonClient.data.repository.UserRepositoryImp
import commonClient.domain.repository.AppThemeSettingsRepository
import commonClient.domain.repository.AuthRepository
import commonClient.domain.repository.CategoryRepository
import commonClient.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(): HttpClient = SabotenApiHttpClient(CIO)
    }

    /* API */
    @Binds
    abstract fun provideUserApi(imp : UserApiImp): UserApi
    @Binds
    abstract fun providePostApi(imp : PostApiImp): PostApi
    @Binds
    abstract fun provideAuthApi(imp : AuthApiImp): AuthApi
    @Binds
    abstract fun provideCategoryApi(imp : CategoryApiImp): CategoryApi

    /* Repositories */
    @Binds
    abstract fun provideUserRepository(imp : UserRepositoryImp): UserRepository

    @Binds
    abstract fun provideCategoryRepository(imp : CategoryRepositoryImp): CategoryRepository
    @Binds
    abstract fun provideAppThemeSettingsRepository(imp : AppThemeSettingsRepositoryImp): AppThemeSettingsRepository

    @Binds
    abstract fun provideAuthRepository(imp : AuthRepositoryImp): AuthRepository

}