package common.model.request.post.update

import common.model.request.post.create.VoteCreateRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostUpdateRequest (
    @SerialName("id") val id: Long,
    @SerialName("text") val text: String,
    @SerialName("voteTopics") val voteTopics: List<VoteCreateRequest>,
    @SerialName("categoryIds") val categoryIds: List<Long>
)