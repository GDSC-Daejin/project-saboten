package common.model.request.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostScrapRequest (
    @SerialName("isScraped") val isScraped: Boolean,
)