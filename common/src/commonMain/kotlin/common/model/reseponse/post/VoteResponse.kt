package common.model.reseponse.post

import common.model.VoteColors
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VoteResponse(
    @SerialName("id") val id : Long,
    @SerialName("topic") val topic : String,
    @SerialName("count") val count : Int,
    @SerialName("color") val color : VoteColors
)