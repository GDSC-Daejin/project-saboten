package common.model.reseponse

import common.message.ResponseMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic



@Serializable
data class ApiResponse<T>(
    @SerialName("data") val data: T?,
    @SerialName("code") val code: String,
    @SerialName("message") val message: String
) {

    companion object {
        @JvmStatic
        fun <T> withMessage(data : T, responseMessage: ResponseMessage) =
            ApiResponse(data, responseMessage.toString(), responseMessage.message)
    }

}