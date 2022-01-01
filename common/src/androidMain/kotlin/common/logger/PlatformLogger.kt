package common.logger

import timber.log.Timber

actual object PlatformLogger {

    actual fun d(message: String) {
        Timber.d(message)
    }

}