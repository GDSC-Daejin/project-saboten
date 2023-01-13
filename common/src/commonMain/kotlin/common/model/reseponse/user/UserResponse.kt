package common.model.reseponse.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("id") val id : Long,
    @SerialName("nickname") val nickname : String,
    @SerialName("profilePhotoUrl") val profilePhotoUrl : String?,
)