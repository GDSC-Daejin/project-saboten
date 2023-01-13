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
    @SerialName("voteResponses") val voteResponses: List<VoteResponse>,
    @SerialName("categories") val categories: List<CategoryResponse>,
    @SerialName("selectedVote") val selectedVote: Long?,
    @SerialName("view") val view: Int,
    @SerialName("likeCount") val likeCount: Int,
    @SerialName("scrapCount") val scrapCount: Int,
    @SerialName("liked") val isLiked: Boolean?,
    @SerialName("scraped") val isScraped: Boolean?,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String?
)
