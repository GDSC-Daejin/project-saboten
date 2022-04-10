package commonClient.di

import com.russhwolf.settings.JsSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import commonClient.utils.ClientProperties
import commonClient.utils.EncryptedSettingsHolder
import commonClient.utils.process
import io.ktor.client.engine.*
import io.ktor.client.engine.js.*
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

val jsKoinModule = module {
    single {
        ClientProperties(
            "app.saboten.web",
            "",
            process.env.NODE_ENV == "development"
        )
    }
    single<HttpClientEngineFactory<*>> { Js }

    single(named("encrypted")) {
        EncryptedSettingsHolder().settings
    }

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