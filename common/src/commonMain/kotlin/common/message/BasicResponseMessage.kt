package common.message

enum class BasicResponseMessage(
    override val message: String,
    override val statusCode: Int
) : ResponseMessage {

    SUCCEED("응답에 성공했습니다.", 200),
    NOT_FOUND("데이터를 찾을 수 없습니다.", 404),
    INTERNAL_SERVER_ERROR("서버에 응답할 수 없습니다.", 500),
    BAD_REQUEST("잘못된 요청입니다.", 400),
    UNAUTHORIZED("인증이 필요합니다.", 401),
    FORBIDDEN("권한이 없습니다.", 403),
    INVALID_PARAMETER("매개변수가 잘못됬습니다.", 400),
    INVALID_JSON("JSON 형식이 잘못되었습니다.", 400),
    INVALID_REFRESH_TOKEN("Refresh Token이 유효하지 않습니다.", 400),
}