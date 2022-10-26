package commonClient.di

import commonClient.data.cache.createDataStore
import commonClient.data.cache.dataStoreFileName
import commonClient.utils.ClientProperties
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module
import platform.Foundation.NSBundle
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

actual fun platformModule() = module {
    single {
        createDataStore(
            producePath = {
                val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
                requireNotNull(documentDirectory).path + "/$dataStoreFileName"
            }
        )
    }

    single<HttpClientEngineFactory<*>> { Darwin }

    single {
        ClientProperties(
            NSBundle.mainBundle.bundleIdentifier.orEmpty(),
            NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString")?.toString().orEmpty(),
            Platform.isDebugBinary
        )
    }
}