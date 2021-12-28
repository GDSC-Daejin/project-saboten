package app.saboten.common.entities

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id : Long,
    val message : String
)