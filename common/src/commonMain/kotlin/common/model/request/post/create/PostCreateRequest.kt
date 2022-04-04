package common.model.request.post.create

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* POST /post */
@Serializable
data class PostCreateRequest(
    @SerialName("text") val text: String,
    @SerialName("vote_topics") val voteTopics: List<VoteCreateRequest>,
    @SerialName("category_ids") val categoryIds: List<Long>
)
