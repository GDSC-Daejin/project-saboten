package commonClient.data.remote

import common.model.reseponse.ApiResponse
import commonClient.data.LoadState
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*

interface Api {

    val httpClient: HttpClient
    val prefixUrl: String

}

suspend inline fun <reified T> Api.responseGet(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return runCatching {
        httpClient.get {
            url { encodedPath = "$prefixUrl$suffixUrl" }
            block()
        }
    }.fold({
        it.body()
    }, {
        when (it) {
            is ClientRequestException -> it.response.body()
            is ServerResponseException -> it.response.body()
            else -> throw it
        }
    })
}

suspend inline fun <reified T> Api.responsePost(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return runCatching {
        httpClient.post {
            url { encodedPath = "$prefixUrl$suffixUrl" }
            block()
        }
    }.fold({
        it.body()
    }, {
        when (it) {
            is ClientRequestException -> it.response.body()
            is ServerResponseException -> it.response.body()
            else -> throw it
        }
    })
}

suspend inline fun <reified T> Api.responsePatch(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return runCatching {
        httpClient.patch {
            url { encodedPath = "$prefixUrl$suffixUrl" }
            block()
        }
    }.fold({
        it.body()
    }, {
        when (it) {
            is ClientRequestException -> it.response.body()
            is ServerResponseException -> it.response.body()
            else -> throw it
        }
    })
}

suspend inline fun <reified T> Api.responseDelete(
    suffixUrl: Any = "/",
    block: HttpRequestBuilder.() -> Unit = {}
): ApiResponse<T> {
    return runCatching {
        httpClient.delete {
            url { encodedPath = "$prefixUrl$suffixUrl" }
            block()
        }
    }.fold({ it.body() }, {
        when (it) {
            is ClientRequestException -> it.response.body()
            is ServerResponseException -> it.response.body()
            else -> throw it
        }
    })
}