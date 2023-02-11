package common.model.reseponse.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("iconUrl") val iconUrl: String,
    @SerialName("startColor") val startColor: String = "#FF0A0A0A",
    @SerialName("endColor") val endColor: String ="#FF0A0A0A",
)