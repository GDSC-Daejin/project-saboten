package commonClient.di

import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.coroutines.toSuspendSettings
import commonClient.utils.ClientProperties
import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.Foundation.NSBundle
import platform.Foundation.NSUserDefaults

private val iosKoinModule = module {
    single<HttpClientEngineFactory<*>> { Darwin }
    single {
        ClientProperties(
            NSBundle.mainBundle.bundleIdentifier.orEmpty(),
            NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString")?.toString().orEmpty(),
            Platform.isDebugBinary
        )
    }
    single {
        AppleSettings(NSUserDefaults.standardUserDefaults).toSuspendSettings()
    }
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