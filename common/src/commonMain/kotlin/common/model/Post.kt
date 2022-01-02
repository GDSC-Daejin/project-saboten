package common.model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id : Long,
    val content : String,
    val chooses : List<Choose>
)