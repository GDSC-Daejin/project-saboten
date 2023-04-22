package app.saboten.androidApp.di

import app.saboten.androidApp.BuildConfig
import commonClient.utils.ClientProperties
import org.koin.dsl.module

val androidAppModule = module {
    single {
        ClientProperties(
            id = BuildConfig.APPLICATION_ID,
            version = BuildConfig.VERSION_NAME,
            isDebug = BuildConfig.DEBUG
        )
    }
}