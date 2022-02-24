package commonClient.extension

import common.model.reseponse.ApiResponse
import common.model.reseponse.message.ResponseMessage

fun ApiResponse<*>.isCodeEquals(responseMessage: ResponseMessage): Boolean =
    code == responseMessage.toString()