package common.model.reseponse.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostLikeResponse (
    @SerialName("like") val like : Boolean
)