package common.message

enum class AuthResponseMessage (
    override val message: String,
    override val statusCode: Int
) : ResponseMessage {
    INVALID_ACCESS_TOKEN("Access Token이 유효하지 않습니다.", 401),
}