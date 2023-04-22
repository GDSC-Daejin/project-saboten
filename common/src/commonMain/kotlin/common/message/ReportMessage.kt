package common.message

enum class ReportMessage (
    override val message: String,
    override val statusCode: Int)
    : ResponseMessage {
    COMMENT_REPORT_SUCCESS("메세지 신고가 완료되었습니다.", 200)
}