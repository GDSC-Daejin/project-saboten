package commonClient.data.remote

import common.model.request.auth.TokenReissueRequest
import common.model.reseponse.ApiResponse
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
    block: HttpClientConfig<T>.() -> Unit = {}
) = HttpClient(engineFactory) {

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
            loadTokens(AuthTokenManager.tokenStorage::lastOrNull)
            refreshTokens {
                val accessToken = AuthTokenManager.tokenStorage.lastOrNull()?.accessToken
                val refreshToken = AuthTokenManager.tokenStorage.lastOrNull()?.refreshToken
                val response = client.post("https://$URL/refresh") {
                    setBody(
                        TokenReissueRequest(
                            accessToken ?: "",
                            refreshToken ?: ""
                        )
                    )
                }.body<ApiResponse<JwtTokenResponse>>().data
                response?.let {
                    AuthTokenManager.addToken(it)
                    BearerTokens(
                        accessToken = it.accessToken,
                        refreshToken = it.refreshToken
                    )
                }
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