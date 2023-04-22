package common.model.reseponse.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JwtTokenResponse (
    @SerialName("grantType") val grantType: String,
    @SerialName("accessToken") val accessToken: String,
    @SerialName("refreshToken") val refreshToken: String,
    @SerialName("accessTokenExpiresIn") val accessTokenExpiresIn: Long,
)