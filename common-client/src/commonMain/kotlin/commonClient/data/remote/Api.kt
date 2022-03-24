package commonClient.data.remote

import common.model.reseponse.ApiResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

interface Api {

    val httpClient: HttpClient
    val prefixUrl: String

}

suspend inline fun <reified T> Api.responseGet(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return httpClient.get(prefixUrl + suffixUrl.toString(), block).body()
}

suspend inline fun <reified T> Api.responsePost(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return httpClient.post(prefixUrl + suffixUrl.toString(), block).body()
}

suspend inline fun <reified T> Api.responsePatch(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return httpClient.patch(prefixUrl + suffixUrl.toString(), block).body()
}

suspend inline fun <reified T> Api.responseDelete(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return httpClient.delete(prefixUrl + suffixUrl.toString(), block).body()
}

