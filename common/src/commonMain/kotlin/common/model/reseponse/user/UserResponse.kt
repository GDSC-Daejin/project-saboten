package common.model.reseponse.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    @SerialName("id") override val id : Long,
    @SerialName("nickname") override val nickname : String,
    @SerialName("profile_photo_url") override val profilePhotoUrl : String,
) : UserModel