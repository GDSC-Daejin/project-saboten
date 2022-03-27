package common.message

enum class UserResponseMessage(
    override val message: String,
    override val statusCode: Int
) : ResponseMessage {

    USER_CREATED("유저가 생성되었습니다.", 201),
    USER_UPDATED("유저가 수정되었습니다.", 200),
    USER_DELETED("유저가 삭제되었습니다.", 200),

    USER_NOT_REGISTERED("유저가 가입되지 않았습니다.", 404),

}