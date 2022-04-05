package common.model.request.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// 일단 소셜로그인 구현하기 전 스프링 시큐리티 (jwt 인증) 테스트 용입니다.
@Serializable
data class UserLoginTestRequest(
    @SerialName("id") val id: Long,
)