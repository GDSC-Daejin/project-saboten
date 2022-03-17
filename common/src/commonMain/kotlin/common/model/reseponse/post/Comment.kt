package common.model.reseponse.post

import common.model.reseponse.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    @SerialName("id") val id: Long,
    @SerialName("text") val text: String,
    @SerialName("author") val author: User,
    @SerialName("selected_vote") val selectedVote: Long?,
    @SerialName("created_at") val createdAt: String,
)