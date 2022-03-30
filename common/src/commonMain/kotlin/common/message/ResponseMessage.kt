package common.message

interface ResponseMessage {
    val message: String
    val statusCode : Int
}
