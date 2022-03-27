package common.message

enum class BasicResponseMessage(
    override val message: String
) : ResponseMessage {

    SUCCEED("응답에 성공했습니다."),
    NOT_FOUND("데이터를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR("서버에 응답할 수 없습니다."),
    BAD_REQUEST("잘못된 요청입니다."),
    UNAUTHORIZED("인증이 필요합니다."),
    FORBIDDEN("권한이 없습니다."),
    INVALID_PARAMETER("매개변수가 잘못됬습니다.")

}