package common.model.reseponse.category

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String
)