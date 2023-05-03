package commonClient.domain.entity.post

import commonClient.domain.entity.user.User
import kotlinx.datetime.LocalDateTime

data class Post(
    val id: Long,
    val text: String,
    val author: User,
    val voteResponses: List<Vote>,
    val categories: List<Category>,
    val selectedVote: Long?,
    val isScraped: Boolean?,
    val isLiked: Boolean?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)
