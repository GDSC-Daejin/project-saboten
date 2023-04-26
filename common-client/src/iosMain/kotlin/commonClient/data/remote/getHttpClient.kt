package commonClient.data.remote

import commonClient.utils.AuthTokenManager
import io.ktor.client.*
import io.ktor.client.engine.darwin.*

actual fun getHttpClient(authTokenManager : AuthTokenManager): HttpClient = SabotenApiHttpClient(authTokenManager, Darwin)