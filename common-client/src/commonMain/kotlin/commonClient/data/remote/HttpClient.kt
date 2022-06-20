package commonClient.data.remote

import common.model.request.auth.TokenReissueRequest
import common.model.reseponse.auth.JwtTokenResponse
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
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json

// TODO Change URL
private const val URL = "localhost:8080"

@Suppress("FunctionName")
fun <T : HttpClientEngineConfig> SabotenApiHttpClient(
    engineFactory: HttpClientEngineFactory<T>,
    authTokenManager : AuthTokenManager,
    properties: ClientProperties,
    block: HttpClientConfig<T>.() -> Unit = {}
) = HttpClient(engineFactory) {

    developmentMode = properties.isDebug
    expectSuccess = false

    install(HttpTimeout) {
        requestTimeoutMillis = 5000L
        connectTimeoutMillis = 5000L
    }

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
                val accessToken = authTokenManager.getAccessToken()
                val refreshToken = authTokenManager.getRefreshToken()
                if (accessToken != null && refreshToken != null) BearerTokens(accessToken, refreshToken)
                else null
            }
            refreshTokens {
                val accessToken = authTokenManager.getAccessToken()
                val refreshToken = authTokenManager.getRefreshToken()
                val response = client.post("https://$URL/refresh") {
                    setBody(TokenReissueRequest(accessToken!!, refreshToken!!))
                }.body<JwtTokenResponse>()
                authTokenManager.setToken(response)
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