package common.model.reseponse.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JwtToken (
    @SerialName("grant_type") val grantType: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("access_token_expiresIn") val accessTokenExpiresIn: Long,
)