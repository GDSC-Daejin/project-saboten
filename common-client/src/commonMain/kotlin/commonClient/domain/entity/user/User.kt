package commonClient.domain.entity.user

data class User(
    val id: Long,
    val nickname: String,
    val profilePhotoUrl: String?,
)