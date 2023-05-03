package commonClient.domain.entity.post

import commonClient.domain.entity.user.User
import kotlinx.datetime.LocalDateTime

data class Comment(
    val id: Long,
    val text: String,
    val author: User,
    val selectedVote: Long?,
    val createdAt: LocalDateTime,
)