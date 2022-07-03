package common.model.request.comment

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* POST /post/{post_id}/comment */
@Serializable
data class CommentCreateRequest(
    @SerialName("text") val text: String
)