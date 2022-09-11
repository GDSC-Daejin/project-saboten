package commonClient.domain.entity.post

import commonClient.domain.entity.user.User

data class Post(
    val id: Long,
    val text: String,
    val author: User,
    val voteResponses: List<Vote>,
    val categories: List<Category>,
    val selectedVote: Long?,
    val isLiked: Boolean?,
    val createdAt: String,
    val updatedAt: String?
)