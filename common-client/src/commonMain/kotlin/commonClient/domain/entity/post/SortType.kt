package commonClient.domain.entity.post

import kotlinx.serialization.Serializable

enum class PeriodType(val text: String) {
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

@Serializable
enum class CategoryType(val text: String, val id: Long) {
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