package common.model.request.user

import common.model.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpRequest(
    @SerialName("nickname") val nickname: String,
    @SerialName("introduction") val introduction: String?,
    @SerialName("age") val age: Int?,
    @SerialName("gender") val gender: Gender?
)