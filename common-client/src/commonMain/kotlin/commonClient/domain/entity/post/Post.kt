package commonClient.domain.entity.post

import commonClient.domain.entity.user.User

data class Post(
    val id: Long,
    val text: String,
    val author: User,
    val voteResponses: List<Vote>,
    val categories: List<Category>,
    val selectedVote: Long?,
    val isScraped: Boolean?,
    val isLiked: Boolean?,
    val createdAt: String,
    val updatedAt: String?
) {
    companion object {
        val loadingPost: Post
            get() = Post(
                0L,
                "로딩 중",
                User(0L, "로딩 중", null),
                listOf(
                    Vote(
                        id = 1,
                        topic = "로딩 중 입니다.",
                        count = 0,
                        color = VoteColors.BLUE
                    ),
                    Vote(
                        id = 2,
                        topic = "로딩 중 입니다.",
                        count = 0,
                        color = VoteColors.BLUE
                    )
                ),
                emptyList(),
                null,
                false,
                isLiked = false,
                createdAt = "2023-02-11T15:36:45.495465",
                updatedAt = null,
            )
    }
}
