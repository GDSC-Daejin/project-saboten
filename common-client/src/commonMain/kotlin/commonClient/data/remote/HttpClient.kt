package commonClient.data.remote

import common.logger.PlatformLogger
import common.utils.Platform
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
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

    developmentMode = Platform.isDevelopmentMode
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
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                PlatformLogger.d(message)
            }
        }
        level = LogLevel.ALL
    }

    defaultRequest {
//        header("Authorization", "Bearer ${authenticator.idToken}")

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