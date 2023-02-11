package commonClient.data.remote

import common.model.request.auth.TokenReissueRequest
import common.model.reseponse.auth.JwtTokenResponse
import commonClient.logger.ClientLogger
import commonClient.utils.AuthTokenManager
import commonClient.utils.ClientProperties
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.KotlinxSerializationConverter
import kotlinx.serialization.json.Json

// TODO Change URL
private const val URL = "saboten.loca.lt"

expect fun getHttpClient(): HttpClient

@Suppress("FunctionName")
internal fun <T : HttpClientEngineConfig> SabotenApiHttpClient(
    engineFactory: HttpClientEngineFactory<T>,
    block: HttpClientConfig<T>.() -> Unit = {},
) = HttpClient(engineFactory) {

    expectSuccess = false

//    install(HttpTimeout) {
//        requestTimeoutMillis = 10000L
//        connectTimeoutMillis = 10000L
//    }

    install(ContentNegotiation) {
        register(
            contentType = ContentType.Application.Json,
            converter = KotlinxSerializationConverter(
                Json {
                    ignoreUnknownKeys = true
                    allowSpecialFloatingPointValues = true
                    useArrayPolymorphism = true
                    prettyPrint = true
                    allowStructuredMapKeys = true
                    coerceInputValues = true
                    useAlternativeNames = false
                }
            )
        )
    }

    install(Auth) {
        bearer {
            loadTokens {
                val token = AuthTokenManager.tokenStorage.lastOrNull()
                val accessToken = token?.accessToken
                val refreshToken = token?.refreshToken
                if (accessToken != null && refreshToken != null) BearerTokens(accessToken, refreshToken)
                else null
            }
            refreshTokens {
                val token = AuthTokenManager.tokenStorage.lastOrNull()
                val accessToken = token?.accessToken
                val refreshToken = token?.refreshToken

                if (accessToken == null || refreshToken == null) return@refreshTokens null

                val response = client.post("https://$URL/refresh") {
                    setBody(TokenReissueRequest(accessToken, refreshToken))
                }.body<JwtTokenResponse>()
                AuthTokenManager.addToken(response)
                BearerTokens(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            }
        }
    }


    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                ClientLogger.d(message)
            }
        }
        level = LogLevel.ALL
    }

    defaultRequest {

        header(HttpHeaders.ContentType, ContentType.Application.Json)

        headers {
            accept(ContentType.Application.Json)
            accept(ContentType.MultiPart.FormData)
        }

        url {
            protocol = URLProtocol.HTTP
            host = URL
        }
    }

    block()
}