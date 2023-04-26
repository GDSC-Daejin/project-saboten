package commonClient.data.remote

import commonClient.utils.AuthTokenManager
import io.ktor.client.*
import io.ktor.client.engine.cio.*

actual fun getHttpClient(authTokenManager: AuthTokenManager): HttpClient = SabotenApiHttpClient(authTokenManager, CIO)