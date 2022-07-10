package common.model.reseponse.comment

import common.model.reseponse.user.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    @SerialName("id") val id: Long,
    @SerialName("text") val text: String,
    @SerialName("author") val author: UserResponse,
    @SerialName("selected_vote") val selectedVote: Long?,
    @SerialName("created_at") val createdAt: String,
)