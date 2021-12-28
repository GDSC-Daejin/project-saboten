package app.saboten.android

import android.app.Application
import app.saboten.android.utils.ReleaseTimberTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.plant(ReleaseTimberTree)
    }

}