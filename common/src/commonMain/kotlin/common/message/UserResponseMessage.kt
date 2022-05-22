package common.message

enum class UserResponseMessage(
    override val message: String,
    override val statusCode: Int
) : ResponseMessage {

    USER_CREATED("유저가 생성되었습니다.", 201),
    USER_READ("유저가 조회되었습니다.",200),
    USER_UPDATED("유저가 수정되었습니다.", 200),
    USER_DELETED("유저가 삭제되었습니다.", 200),
    USER_LOGIN("유저가 로그인되었습니다.", 200),
    USER_LOGOUT("유저가 로그아웃되었습니다.", 200),
    USER_MISMATCH_PROVIDER_TYPE("유저의 소셜 로그인 종류가 다릅니다.", 404),
    USER_TOKEN_REISSUE("유저가 토큰을 갱신하였습니다.", 200),
    USER_NOT_FOUND("유저가 존재하지 않습니다.", 404),

    USER_NOT_REGISTERED("유저가 가입되지 않았습니다.", 404),
    USER_ALREADY_REGISTERED("유저가 이미 가입되었습니다.", 409),
}