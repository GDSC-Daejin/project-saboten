package commonClient.data.remote

import io.ktor.client.*
import io.ktor.client.engine.cio.*

actual fun getHttpClient(): HttpClient = SabotenApiHttpClient(CIO)