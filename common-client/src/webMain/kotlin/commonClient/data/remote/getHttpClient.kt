package commonClient.data.remote

import io.ktor.client.*
import io.ktor.client.engine.js.*

actual fun getHttpClient(): HttpClient = SabotenApiHttpClient(Js)