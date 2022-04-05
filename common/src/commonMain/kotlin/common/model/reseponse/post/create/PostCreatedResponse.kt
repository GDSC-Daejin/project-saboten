package common.model.reseponse.post.create

import common.model.reseponse.category.Category
import common.model.reseponse.post.Vote
import common.model.reseponse.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostCreatedResponse(
    @SerialName("id") val id: Long,
    @SerialName("text") val text: String,
    @SerialName("author") val author: User,
    @SerialName("votes") val votes: List<Vote>,
    @SerialName("categories") val categories: List<Category>,
    @SerialName("created_at") val createdAt: String,
)