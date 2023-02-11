package app.saboten.androidApp.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import commonClient.data.LoadState
import commonClient.domain.entity.user.UserInfo

val LocalMeState = compositionLocalOf<LoadState<UserInfo?>> { LoadState.idle() }

/**
 * CompositionLocalProvider 를 이용해서 어디서든지 업데이트 된 내 UserInfo 를 사용할 수 있도록 합니다.
 * 예: val me : UserInfo? = LocalMeInfo.current
 * */
@Composable
fun ProvideMeInfo(userInfo: LoadState<UserInfo?>, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalMeState provides userInfo, content = content)
}