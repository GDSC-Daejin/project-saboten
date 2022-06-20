package app.saboten.androidApp.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import common.model.reseponse.user.UserInfoResponse
import commonClient.domain.entity.user.User
import commonClient.domain.entity.user.UserInfo

val LocalMeInfo = compositionLocalOf<UserInfo?> { null }

/**
 * CompositionLocalProvider 를 이용해서 어디서든지 업데이트 된 내 UserInfo 를 사용할 수 있도록 합니다.
 * 예: val me : UserInfo? = LocalMeInfo.current
 * */
@Composable
fun ProvideMeInfo(userInfo: UserInfo?, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalMeInfo provides userInfo, content = content)
}