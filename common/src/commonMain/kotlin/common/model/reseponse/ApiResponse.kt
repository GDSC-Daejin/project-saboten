package common.model.reseponse

import common.message.ResponseMessage
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmStatic

@Serializable
data class ApiResponse<T>(
    val data: T?,
    val code: String,
    val message: String
) {

    companion object {
        @JvmStatic
        fun <T> withMessage(data : T, responseMessage: ResponseMessage) =
            ApiResponse(data, responseMessage.toString(), responseMessage.message)
    }

}