package app.saboten.androidApp

import android.app.Application
import app.saboten.androidApp.di.androidAppModule
import commonClient.di.sharedModule
import commonClient.logger.ClientLogger
import commonClient.utils.ClientProperties
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AndroidApp : Application() {

    private val clientProperties by inject<ClientProperties>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AndroidApp)
            modules(
                androidAppModule,
                sharedModule()
            )
        }

        ClientLogger.init(clientProperties)
    }

}