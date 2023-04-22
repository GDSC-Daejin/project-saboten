package common.message

enum class CommentResponseMessage(
    override val message: String,
    override val statusCode: Int
) : ResponseMessage {
    COMMENT_CREATED("댓글이 생성되었습니다.", 201),
    COMMENT_UPDATED("댓글이 수정되었습니다.", 200),
    COMMENT_DELETED("댓글이 삭제되었습니다.", 200),
    COMMENT_FIND_USER("해당 유저가 쓴 댓글들이 조회되었습니다.",200),
    COMMENT_FIND_ALL("댓글들이 조회되었습니다.", 200),
    COMMENT_IS_NULL("요청 댓글형식이 올바르지 않습니다.",400),
    COMMENT_NOT_FOUND("댓글이 존재하지 않습니다.", 404),
}