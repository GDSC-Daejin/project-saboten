package commonClient.logger

import android.util.Log
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import commonClient.utils.ClientProperties
import timber.log.Timber

actual object ClientLogger {

    actual fun init(clientProperties: ClientProperties) {
        if (clientProperties.isDebug) Timber.plant(Timber.DebugTree())
        else {
            Timber.plant(
                object : Timber.Tree() {
                    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                        when (priority) {
                            Log.WARN, Log.ERROR -> t?.let {
                                val newThrowable = Throwable().initCause(t).apply {
                                    stackTrace = Thread.currentThread().stackTrace
                                }
                                Firebase.crashlytics.recordException(newThrowable)
                            }
                            else -> Firebase.crashlytics.log("$tag | $message")
                        }
                    }
                }
            )
        }
    }

    actual fun d(message: String) {
        Timber.d("🌵 DEBUG 🟢 : $message")
    }

    actual fun i(message: String) {
        Timber.i("🌵 INFO 🔵 : $message")
    }

    actual fun w(message: String) {
        Timber.w("🌵 WARN 🟡 : $message")
    }

    actual fun e(throwable: Throwable) {
        Timber.e(throwable, "🌵 ERROR 🔴 : ${throwable.message}")
    }

}