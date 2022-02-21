package common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id") val id : Long,
    @SerialName("nickname") val nickname : String,
    @SerialName("profile_photo_url") val profilePhotoUrl : String
)