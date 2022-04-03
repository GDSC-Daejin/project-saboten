package common.message

enum class PostResponseMessage(
    override val message: String,
    override val statusCode: Int
) : ResponseMessage {
    POST_CREATED("게시글이 생성되었습니다.", 201),
    POST_UPDATED("게시글이 수정되었습니다.", 200),
    POST_DELETED("게시글이 삭제되었습니다.", 200),
    POST_NOT_FOUND("게시글이 존재하지 않습니다.", 404),
}