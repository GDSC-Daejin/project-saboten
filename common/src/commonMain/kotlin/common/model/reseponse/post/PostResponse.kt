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
    @SerialName("selected_vote") val selectedVote: Long?,
    @SerialName("view") val view: Int,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("scrap_count") val scrapCount: Int,
    @SerialName("is_liked") val isLiked: Boolean?,
    @SerialName("is_scraped") val isScraped: Boolean?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String?
)