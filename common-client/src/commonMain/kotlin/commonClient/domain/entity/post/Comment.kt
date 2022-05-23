package commonClient.domain.entity.post

import commonClient.domain.entity.user.User

data class Comment(
    val id: Long,
    val text: String,
    val author: User,
    val selectedVote: Long?,
    val createdAt: String,
)