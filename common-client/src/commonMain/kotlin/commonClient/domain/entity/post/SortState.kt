package commonClient.domain.entity.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

@Serializable
data class PeriodState(val type: PeriodType = PeriodType.WEEK)

@Serializable
data class CategoryState(val type: CategoryType = CategoryType.ALL)

@Serializable
data class HotPostSortState(
    @SerialName("periodState") var periodState: PeriodState = PeriodState(),
    @SerialName("categoryState") var categoryState: CategoryState = CategoryState()
)
