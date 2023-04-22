package common.message

enum class BannerResponseMessage (
    override val message: String,
    override val statusCode: Int
) : ResponseMessage {

    BANNER_FIND_ALL("배너 전체 조회가 성공했습니다.", 200),
    BANNER_NOT_FOUND("조회하려는 카테고리가 없습니다.", 404),
}