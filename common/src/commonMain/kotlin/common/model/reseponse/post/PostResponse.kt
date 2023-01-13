package common.model.reseponse.post

import common.model.VoteColorsResponse
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
    @SerialName("likeCount") val likeCount: Int? = null,
    @SerialName("scrapCount") val scrapCount: Int? = null,
    @SerialName("isLiked") val isLiked: Boolean? = null,
    @SerialName("isScraped") val isScraped: Boolean? = null,
    @SerialName("createdAt") val createdAt: String,
    @SerialName("updatedAt") val updatedAt: String? = null,
) {

    companion object {
        val MOCK = PostResponse(
            id = 1,
            text = "text",
            author = UserResponse(
                id = 1,
                nickname = "nickname",
                profilePhotoUrl = null,
            ),
            voteResponses = listOf(
                VoteResponse(
                    id = 1,
                    topic = "topic 1",
                    count = 324,
                    color = VoteColorsResponse.WHITE,
                ),
                VoteResponse(
                    id = 2,
                    topic = "topic 2",
                    count = 10,
                    color = VoteColorsResponse.BLACK,
                )
            ),
            categories = listOf(
                CategoryResponse(
                    id = 1,
                    name = "name",
                    iconUrl = "https://github.com/GDSC-Daejin/project-saboten-iconpack/blob/master/ic_category_all.png?raw=true",
                )
            ),
            selectedVote = 1,
            view = 1,
            likeCount = 1,
            scrapCount = 1,
            isLiked = true,
            isScraped = true,
            createdAt = "2021-01-01",
            updatedAt = "2021-01-01",
        )
    }

}