package common.model.request.post.create

import common.model.VoteColorsResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteCreateRequest(
    @SerialName("topic") val topic : String,
    @SerialName("color") val color : VoteColorsResponse
)