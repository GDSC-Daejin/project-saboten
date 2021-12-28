package app.saboten.android

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}