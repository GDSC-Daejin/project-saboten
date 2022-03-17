package common.model.request.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* POST /post/{post_id}/vote */
@Serializable
data class VoteSelectRequest(
    @SerialName("id") val id: Long,
)