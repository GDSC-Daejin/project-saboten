package commonClient.domain.entity.user

data class UserInfo(
    val id: Long,
    val nickname: String,
    val profilePhotoUrl: String,
    val email: String,
    val introduction: String,
    val age: Int?,
    val gender: Gender?
)