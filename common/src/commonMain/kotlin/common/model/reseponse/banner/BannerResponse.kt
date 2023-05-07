package common.model.reseponse.banner

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BannerResponse(
    @SerialName("id") val id: Long,
    @SerialName("title") val title: String,
    @SerialName("subtitle") val subtitle: String,
    @SerialName("category") val category: String,
    @SerialName("icon_url") val iconUrl: String
)
