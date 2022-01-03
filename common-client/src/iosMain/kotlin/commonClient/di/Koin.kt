package commonClient.di

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val iosKoinModule = module {
    single<HttpClientEngineFactory<*>> { Darwin }
}

fun initKoin() {

    startKoin {
        modules(
            iosKoinModule,
            dataModule(),
            domainModule(),
            presentationModule()
        )
    }

}