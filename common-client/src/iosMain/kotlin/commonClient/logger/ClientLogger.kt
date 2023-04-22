package commonClient.logger

import commonClient.utils.ClientProperties

@ThreadLocal
actual object ClientLogger {

    private var isDebug = false

    actual fun init(clientProperties: ClientProperties) {
        isDebug = clientProperties.isDebug
    }

    actual fun d(message: String) {
        if (isDebug) {
            println("🌵 DEBUG 🟢 : $message")
        }
    }

    actual fun i(message: String) {
        if (isDebug) {
            println("🌵 INFO 🔵 : $message")
        }
    }

    actual fun w(message: String) {
        if (isDebug) {
            println("🌵 WARN 🟡 : $message")
        }
    }

    actual fun e(throwable: Throwable) {
        if (isDebug) {
            println("🌵 ERROR 🔴 : ${throwable.message}")
        }
    }

}