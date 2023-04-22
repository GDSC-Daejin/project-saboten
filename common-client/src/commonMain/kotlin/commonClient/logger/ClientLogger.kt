package commonClient.logger

import commonClient.utils.ClientProperties

expect object ClientLogger {

    fun init(clientProperties: ClientProperties)

    fun d(message: String)
    fun i(message: String)
    fun w(message: String)
    fun e(throwable: Throwable)

}