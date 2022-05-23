package common.model.request.user

import common.model.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSignUpRequest(
    //TODO : age, gender, email 은 처음 회원가입시 1회만 수정(입력)이 가능하고 이후 유저정보 수정에서는 빠져야할 듯
    @SerialName("nickname") val nickname: String,
    @SerialName("introduction") val introduction: String?,
    @SerialName("age") val age: Int?,
    @SerialName("gender") val gender: Gender?,
    @SerialName("profile_image") val profileImage: String?
)