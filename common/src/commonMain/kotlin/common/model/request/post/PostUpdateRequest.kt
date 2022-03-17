package common.model.request.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* PATCH /post/{post_id} */
@Serializable
data class PostUpdateRequest(
    @SerialName("title") val title: String,
    @SerialName("text") val text: String,
    @SerialName("vote_topics") val voteTopics: List<String>,
    @SerialName("category_ids") val categoryIds: List<Long>
)
