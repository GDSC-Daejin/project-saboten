package commonClient.logger

import commonClient.utils.ClientProperties

actual object ClientLogger {

    private var isDebug = false

    actual fun init(clientProperties: ClientProperties) {
        isDebug = clientProperties.isDebug
    }

    actual fun d(message: String) {
        if (isDebug) console.log("🌵 DEBUG 🟢 : $message")

    }

    actual fun i(message: String) {
        if (isDebug) console.info("🌵 INFO 🔵 : $message")

    }

    actual fun w(message: String) {
        if (isDebug) console.warn("🌵 WARN 🟡 : $message")

    }

    actual fun e(throwable: Throwable) {
        if (isDebug) console.error("🌵 ERROR 🔴 : ${throwable.message}")

    }

}