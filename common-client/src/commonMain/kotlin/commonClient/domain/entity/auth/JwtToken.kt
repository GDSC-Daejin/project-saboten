package commonClient.domain.entity.auth

data class JwtToken(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: Long,
)