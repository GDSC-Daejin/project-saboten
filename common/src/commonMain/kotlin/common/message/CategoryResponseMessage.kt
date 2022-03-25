package common.message

enum class CategoryResponseMessage(
    override val message: String
) : ResponseMessage {

    CATEGORY_FIND_ALL("카테고리 전체 조회가 성공했습니다."),
    CATEGORY_FIND_ONE("특정 카테고리 조회가 성공했습니다.")
}