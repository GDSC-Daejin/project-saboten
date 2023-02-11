package common.model.request.report

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentReportCreateRequest(
    @SerialName("text") val text: String
)
