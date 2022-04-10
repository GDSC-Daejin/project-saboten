package common.model.reseponse.user

import common.model.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("id") override val id: Long,
    @SerialName("nickname") override val nickname: String,
    @SerialName("profile_photo_url") override val profilePhotoUrl: String,
    @SerialName("email") val email: String,
    @SerialName("introduction") val introduction: String,
    @SerialName("age") val age: Int?,
    @SerialName("gender") val gender: Gender?
)  : UserModel