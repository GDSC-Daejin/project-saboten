package common.model.request.post.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* POST /post */
@Serializable
data class PostCreateRequest(
    @SerialName("text") val text: String,
    @SerialName("voteTopics") val voteTopics: List<VoteCreateRequest>,
    @SerialName("categoryIds") val categoryIds: List<Long>
)
