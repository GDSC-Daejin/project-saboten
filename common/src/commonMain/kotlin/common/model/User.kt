package common.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id : Long,
    val nickname : String,
    val profilePhotoUrl : String
)