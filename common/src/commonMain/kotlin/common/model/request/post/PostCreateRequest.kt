package common.model.request.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* POST /post */
@Serializable
data class PostCreateRequest(
    @SerialName("text") val text: String,
    @SerialName("vote_topics") val voteTopics: List<String>,
    @SerialName("category_ids") val categoryIds: List<Long>
)
