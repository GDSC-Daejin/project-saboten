package di

import com.russhwolf.settings.JsSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import commonClient.di.dataModule
import commonClient.di.domainModule
import commonClient.di.presentationModule
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import org.koin.core.context.startKoin
import org.koin.dsl.module

val jsKoinModule = module {
    single<HttpClientEngineFactory<*>> { Js }
    single { JsSettings().toSuspendSettings() }
}

fun initKoin() {
    startKoin {
        modules(
            jsKoinModule,
            dataModule(),
            domainModule(),
            presentationModule()
        )
    }
}