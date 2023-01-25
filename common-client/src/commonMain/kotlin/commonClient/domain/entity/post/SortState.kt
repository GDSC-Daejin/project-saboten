package commonClient.domain.entity.post

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
interface SortState {
    val type: SortType
}

data class PeriodState(
    override val type: SortType = PeriodType.DAY
) : SortState

data class CategoryState(
    override val type: SortType = CategoryType.ALL
) : SortState

data class HotPostSortState(
    var periodState: PeriodState = PeriodState(),
    var categoryState: CategoryState = CategoryState()
) {
    fun toJsonString(): String {
        return """
        {"periodState":"${this.periodState.type}","categoryState":"${this.categoryState.type}"}
        """.trimIndent()
    }
}

fun String.toHotPostSortState(): HotPostSortState {
    val jsonObject = Json.parseToJsonElement(this).jsonObject
    val periodType = jsonObject["periodState"].toString().replace("\"","")
    val categoryType = jsonObject["categoryState"].toString().replace("\"","")
    return HotPostSortState(
        periodState = PeriodState(
            if (periodType.isNotBlank()) PeriodType.valueOf(periodType) else PeriodType.DAY
        ),
        categoryState = CategoryState(
            if (categoryType.isNotBlank()) CategoryType.valueOf(categoryType) else CategoryType.ALL
        )
    )
}
