package common.model.reseponse.banner

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("text") val text: String,
    @SerialName("icon_url") val iconUrl: String
)
