package common.model.request.user

import common.model.GenderResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    @SerialName("nickname") val nickname: String,
    @SerialName("introduction") val introduction: String?,
    @SerialName("age") val age: Int?,
    @SerialName("gender") val gender: GenderResponse?
)