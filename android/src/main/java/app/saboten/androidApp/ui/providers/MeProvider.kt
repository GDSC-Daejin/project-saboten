package app.saboten.androidApp.ui.providers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import common.model.reseponse.user.UserInfo

val LocalMeInfo = compositionLocalOf<UserInfo?> { null }

@Composable
fun ProvideMeInfo(userInfo: UserInfo?, content : @Composable () -> Unit) {
    CompositionLocalProvider(LocalMeInfo provides userInfo, content = content)
}