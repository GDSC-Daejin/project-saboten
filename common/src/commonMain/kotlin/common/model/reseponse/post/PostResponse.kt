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
    @SerialName("selectedVote") val selectedVote: Long? = null,
    @SerialName("view") val view: Int? = null,
    @SerialName("mark") val mark: Boolean? = null, // TODO
    @SerialName("likeCount") val likeCount: Int? = null,
    @SerialName("scrapCount") val scrapCount: Int? = null,
    @SerialName("isLiked") val isLiked: Boolean? = null,
    @SerialName("isScraped") val isScraped: Boolean? = null,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String? = null,
)