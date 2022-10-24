package common.message

enum class PostResponseMessage(
    override val message: String,
    override val statusCode: Int
) : ResponseMessage {
    POST_CREATED("게시글이 생성되었습니다.", 201),
    POST_UPDATED("게시글이 수정되었습니다.", 200),
    POST_DELETED("게시글이 삭제되었습니다.", 200),
    POST_FIND_ONE("특정 게시글 조회가 성공했습니다.", 200),
    POST_FIND_USER("해당 유저가 쓴 게시글들이 조회되었습니다.",200),
    POST_FIND_ALL("게시글들이 조회되었습니다.", 200),
    POST_NOT_FOUND("게시글이 존재하지 않습니다.", 404),
    POST_LIKE_SUCCESS("좋아요를 하였습니다.", 200),
    POST_UNLIKE_SUCCESS("좋아요를 취소하였습니다.", 200),
    POST_SCRAP_SUCCESS("게시글을 스크랩 하였습니다.", 200),
    POST_CANCEL_SCRAP_SUCCESS("스크랩을 취소 하였습니다.", 200),
    POST_SCRAP_FIND_SUCCESS("스크랩 게시물들을 조회하였습니다.", 200)
}