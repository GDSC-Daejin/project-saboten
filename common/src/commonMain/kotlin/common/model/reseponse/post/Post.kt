package common.model.reseponse.post

import common.model.reseponse.category.Category
import common.model.reseponse.user.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

@Serializable
data class Post(
    @SerialName("id") val id: Long,
    @SerialName("text") val text: String,
    @SerialName("author") val author: User,
    @SerialName("votes") val votes: List<Vote>,
    @SerialName("categories") val categories: List<Category>,
    @SerialName("selected_vote") val selectedVote: Long?,
    @SerialName("is_liked") val isLiked: Boolean?,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String?
)