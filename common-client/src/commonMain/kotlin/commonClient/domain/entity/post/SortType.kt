package commonClient.domain.entity.post

interface SortType {
    val text: String
}

enum class PeriodType(override val text: String) : SortType {
    DAY("하루동안"),
    WEEK("일주일간"),
    MONTH("한달간"),
    ALL_PERIOD("모든 기간"),
}

enum class CategoryType(override val text: String) : SortType {
    ALL("전체"),
    LOVE("연애"),
    SHOPPING("쇼핑"),
    WORK("일/취업"),
    MBTI("MBTI"),
    FOOD("음식"),
    DAILY("일상"),
    SOCIAL("사회"),
    MONEY("재테크"),
    ETC("기타"),
}