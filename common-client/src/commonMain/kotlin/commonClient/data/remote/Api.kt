package commonClient.data.remote

import common.model.reseponse.ApiResponse
import commonClient.data.LoadState
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

interface Api {

    val httpClient: HttpClient get() = getHttpClient()
    val prefixUrl: String

}

suspend inline fun <reified T> Api.responseGet(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return httpClient.get {
        url { encodedPath = "$prefixUrl$suffixUrl" }
        block()
    }.body()
}

suspend inline fun <reified T> Api.responsePost(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return httpClient.post {
        url { encodedPath = "$prefixUrl$suffixUrl" }
        block()
    }.body()
}

suspend inline fun <reified T> Api.responsePatch(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return httpClient.patch {
        url { encodedPath = "$prefixUrl$suffixUrl" }
        block()
    }.body()
}

suspend inline fun <reified T> Api.responseDelete(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return httpClient.delete {
        url { encodedPath = "$prefixUrl$suffixUrl" }
        block()
    }.body()
}