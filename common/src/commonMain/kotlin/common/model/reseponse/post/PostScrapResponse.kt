package common.model.reseponse.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostScrapResponse (
    @SerialName("scrap") val scrap : Boolean
)