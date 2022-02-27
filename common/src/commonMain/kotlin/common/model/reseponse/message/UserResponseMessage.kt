package common.model.reseponse.message

enum class UserResponseMessage(
    override val message: String
) : ResponseMessage {

    USER_CREATED("유저가 생성되었습니다."),
    USER_UPDATED("유저가 수정되었습니다."),
    USER_DELETED("유저가 삭제되었습니다."),

    USER_NOT_REGISTERED("유저가 가입되지 않았습니다."),

}