package app.saboten.androidApp

import android.app.Application
import commonClient.logger.ClientLogger
import commonClient.utils.ClientProperties
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AndroidApp : Application() {

    @Inject lateinit var clientProperties: ClientProperties

    override fun onCreate() {
        super.onCreate()
        ClientLogger.init(clientProperties)
    }

}