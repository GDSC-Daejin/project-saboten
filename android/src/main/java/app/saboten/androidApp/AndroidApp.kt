package app.saboten.androidApp

import android.app.Application
import app.saboten.androidApp.utils.initializeTimber
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber(isDebug = BuildConfig.DEBUG)
    }

}