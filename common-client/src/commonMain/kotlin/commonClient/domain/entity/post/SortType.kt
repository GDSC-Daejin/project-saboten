package commonClient.domain.entity.post

interface SortType {
    val text: String
}

enum class PeriodType(override val text: String) : SortType {
    DAY("하루동안"),
    WEEK("일주일간"),
    MONTH("한달간"),
    ALL_PERIOD("모든 기간");

    fun toDuration(): Duration {
        return when (this) {
            DAY -> Duration.DAY
            WEEK -> Duration.WEEK
            MONTH -> Duration.MONTH
            ALL_PERIOD -> Duration.ALL
        }
    }
}

enum class CategoryType(override val text: String, val id: Long) : SortType {
    ALL("전체", 10),
    LOVE("연애", 1),
    SHOPPING("쇼핑", 4),
    WORK("일/취업", 5),
    MBTI("MBTI", 3),
    FOOD("음식", 2),
    DAILY("일상", 6),
    SOCIAL("사회", 7),
    MONEY("재테크", 9),
    ETC("기타", 8),
}