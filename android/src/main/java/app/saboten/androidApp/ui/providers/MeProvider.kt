package app.saboten.androidApp.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import app.saboten.androidApp.ui.screens.LocalOpenLoginDialogEffect
import commonClient.data.LoadState
import commonClient.domain.entity.user.UserInfo

class MeInfo(
    val userInfo: LoadState<UserInfo?>,
) {

    val notNullUserInfo: UserInfo get() = requireNotNull(userInfo.getDataOrNull())

    val needLogin get() = userInfo is LoadState.Success && userInfo.data == null

}

val LocalMeInfo = compositionLocalOf<MeInfo> { error("Not Provided!") }

/**
 * CompositionLocalProvider 를 이용해서 어디서든지 업데이트 된 내 UserInfo 를 사용할 수 있도록 합니다.
 * 예: val me : UserInfo? = LocalMeInfo.current
 * */
@Composable
fun ProvideMeInfo(userInfo: LoadState<UserInfo?>, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalMeInfo provides MeInfo(userInfo), content = content)
}