package common.model.reseponse.post

import common.model.reseponse.category.CategoryResponse
import common.model.reseponse.user.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    @SerialName("id") val id: Long,
    @SerialName("text") val text: String,
    @SerialName("author") val author: UserResponse,
    @SerialName("votes") val voteResponses: List<VoteResponse>,
    @SerialName("categories") val categories: List<CategoryResponse>,
    @SerialName("selected_vote") val selectedVote: Int?,
    @SerialName("is_liked") val isLiked: Boolean?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String?
)