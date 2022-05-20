package common.model.reseponse.post.read

import common.model.reseponse.post.VoteResponse
import common.model.reseponse.user.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//내가 쓴 게시글 DTO
@Serializable
data class PostReadResponse (
    @SerialName("id") val id: Long,
    @SerialName("text") val text: String,
    @SerialName("author") val author: UserResponse,
    @SerialName("votes") val votes: List<VoteResponse>,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String?
)