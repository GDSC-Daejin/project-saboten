package common.model.request.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginRequest (
    @SerialName("accessToken") val accessToken : String
)