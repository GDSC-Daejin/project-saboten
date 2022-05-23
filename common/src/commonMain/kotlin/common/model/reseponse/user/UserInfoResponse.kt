package common.model.reseponse.user

import common.model.GenderResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("id") val id: Long,
    @SerialName("nickname") val nickname: String,
    @SerialName("profile_photo_url") val profilePhotoUrl: String,
    @SerialName("email") val email: String,
    @SerialName("introduction") val introduction: String,
    @SerialName("age") val age: Int?,
    @SerialName("gender") val gender: GenderResponse?
)