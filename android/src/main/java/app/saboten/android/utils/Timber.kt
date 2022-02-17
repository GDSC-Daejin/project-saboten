package app.saboten.android.utils

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

fun initializeTimber(isDebug : Boolean) {
    if (isDebug) {
        Timber.plant(Timber.DebugTree())
    } else {
        Timber.plant(object : Timber.Tree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                when (priority) {
                    Log.WARN, Log.ERROR -> t?.let {
                        val newThrowable = Throwable().initCause(t).apply {
                            stackTrace = Thread.currentThread().stackTrace
                        }

                        FirebaseCrashlytics.getInstance().recordException(newThrowable)
                    }
                    else -> FirebaseCrashlytics.getInstance().log("$tag | $message")
                }
            }
        })
    }
}