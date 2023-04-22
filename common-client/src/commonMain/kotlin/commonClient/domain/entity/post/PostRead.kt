package commonClient.domain.entity.post

import common.model.reseponse.post.VoteResponse
import common.model.reseponse.user.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class PostRead (
    val id: Long,
    val text: String,
    val author: UserResponse,
    val votes: List<VoteResponse>,
    val createdAt: String,
    val updatedAt: String?
)