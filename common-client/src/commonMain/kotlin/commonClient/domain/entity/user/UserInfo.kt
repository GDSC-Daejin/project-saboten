package commonClient.domain.entity.user

data class UserInfo(
    val id: Long,
    val nickname: String,
    val profilePhotoUrl: String,
    val email: String,
)