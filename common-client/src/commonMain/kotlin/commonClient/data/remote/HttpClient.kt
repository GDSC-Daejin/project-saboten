package commonClient.data.remote

import common.model.request.auth.TokenReissueRequest
import common.model.reseponse.ApiResponse
import common.model.reseponse.auth.JwtTokenResponse
import commonClient.logger.ClientLogger
import commonClient.utils.AuthTokenManager
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
private const val URL = "pitapat-adventcalendar.site"

expect fun getHttpClient(authTokenManager: AuthTokenManager): HttpClient

private val json =
    Json {
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        useArrayPolymorphism = true
        prettyPrint = true
        allowStructuredMapKeys = true
        coerceInputValues = true
        useAlternativeNames = false
    }

@Suppress("FunctionName")
internal fun <T : HttpClientEngineConfig> SabotenApiHttpClient(
    authTokenManager: AuthTokenManager,
    engineFactory: HttpClientEngineFactory<T>,
    block: HttpClientConfig<T>.() -> Unit = {},
) = HttpClient(engineFactory) {

    expectSuccess = false

    install(HttpTimeout) {
        requestTimeoutMillis = 10000L
        connectTimeoutMillis = 10000L
    }

    install(ContentNegotiation) {
        register(
            contentType = ContentType.Application.Json,
            converter = KotlinxSerializationConverter(
                json
            )
        )
    }

    install(Auth) {
        bearer {
            loadTokens {
                val accessToken = authTokenManager.accessToken()
                val refreshToken = authTokenManager.refreshToken()
                println("🗝️ accessToken: $accessToken")
                println("🗝️ refreshToken: $refreshToken")
                if (accessToken != null && refreshToken != null) BearerTokens(accessToken, refreshToken)
                else null
            }
            refreshTokens {
                val accessToken = authTokenManager.accessToken()
                val refreshToken = authTokenManager.refreshToken()

                kotlin.runCatching {
                    val jwtTokenResponse = client.post("https://$URL/api/v1/auth/reissue") {
                        markAsRefreshTokenRequest()
                        setBody(TokenReissueRequest(accessToken!!, refreshToken!!))
                        contentType(ContentType.Application.Json)
                    }.body<ApiResponse<JwtTokenResponse>>()

                    println("🗝️ new accessToken: ${jwtTokenResponse.data!!.accessToken}")
                    println("🗝️ new refreshToken: ${jwtTokenResponse.data!!.refreshToken}")

                    authTokenManager.setToken(jwtTokenResponse.data!!)

                    BearerTokens(
                        accessToken = jwtTokenResponse.data!!.accessToken,
                        refreshToken = jwtTokenResponse.data!!.refreshToken
                    )
                }
                    .onFailure {
                        println("🗝️ ${it.message}")
                        authTokenManager.clear()
                    }
                    .getOrNull()
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
            protocol = URLProtocol.HTTPS
            host = URL
        }
    }

    block()
}