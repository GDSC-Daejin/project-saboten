package commonClient.domain.mapper

import common.model.GenderResponse
import common.model.reseponse.user.UserInfoResponse
import common.model.reseponse.user.UserResponse
import commonClient.domain.entity.user.Gender
import commonClient.domain.entity.user.User
import commonClient.domain.entity.user.UserInfo

fun UserInfoResponse.toDomain(): UserInfo {
    return UserInfo(
        id = id,
        nickname = nickname,
        profilePhotoUrl = profilePhotoUrl,
        email = email,
        introduction = introduction,
        age = age,
        gender = when (gender) {
            GenderResponse.M -> Gender.M
            GenderResponse.F -> Gender.F
            null -> null
        },
    )
}

fun UserResponse.toDomain() : User {
    return User(id, nickname, profilePhotoUrl)
}