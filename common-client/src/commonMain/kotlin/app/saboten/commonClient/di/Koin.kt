package app.saboten.commonClient.di

import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            domainModule,
            presentationModule
        )
    }
}