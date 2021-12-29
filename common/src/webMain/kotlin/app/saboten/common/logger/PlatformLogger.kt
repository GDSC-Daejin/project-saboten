package app.saboten.common.logger

actual object PlatformLogger {

    actual fun d(message: String) {
        console.log(message)
    }

}