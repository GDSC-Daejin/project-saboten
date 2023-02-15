package common.model.reseponse.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCountResponse (
    @SerialName("myPost") val myPost: Long,
    @SerialName("votedPost") val votedPost : Long,
    @SerialName("scrapedPost") val scrapedPost : Long
)