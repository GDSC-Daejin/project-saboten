package app.saboten.androidApp.di

import app.saboten.androidApp.BuildConfig
import commonClient.utils.ClientProperties
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    companion object {
        @Provides
        @Singleton
        fun provideAppProperties(): ClientProperties = ClientProperties(
            id = BuildConfig.APPLICATION_ID,
            version = BuildConfig.VERSION_NAME,
            isDebug = BuildConfig.DEBUG
        )
    }

}