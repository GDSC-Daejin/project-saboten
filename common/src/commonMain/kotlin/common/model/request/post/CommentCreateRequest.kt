package common.model.request.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* POST /post/{post_id}/comment */
@Serializable
data class CommentCreateRequest(
    @SerialName("text") val text: String
)