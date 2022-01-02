package common.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id : Long,
    val message : String
)