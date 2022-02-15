package app.saboten.android

import android.app.Application
import app.saboten.android.utils.initializeTimber
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber(isDebug = BuildConfig.DEBUG)
    }

}