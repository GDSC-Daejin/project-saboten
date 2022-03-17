package commonClient.data.remote

import io.ktor.client.*

interface Api {

    val httpClient : HttpClient
    val prefixUrl : String

}

