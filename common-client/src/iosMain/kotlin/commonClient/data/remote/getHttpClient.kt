package commonClient.data.remote

import io.ktor.client.*
import io.ktor.client.engine.darwin.*

actual fun getHttpClient(): HttpClient = SabotenApiHttpClient(Darwin)