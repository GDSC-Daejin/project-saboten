package commonClient.di

import app.saboten.commonClient.BuildConfig
import commonClient.data.remote.SabotenApiHttpClient
import commonClient.data.remote.UserApi
import commonClient.data.remote.UserApiImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.cio.*

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {

        @Provides
        fun provideHttpClient() = SabotenApiHttpClient(CIO)

    }

    @get:[Binds]
    val UserApiImp.userApi : UserApi

}