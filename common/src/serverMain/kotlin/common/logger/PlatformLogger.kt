package common.logger

actual object PlatformLogger {

    actual fun d(message: String) {
        println(message)
    }

}