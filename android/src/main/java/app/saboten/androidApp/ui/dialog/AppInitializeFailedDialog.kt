package app.saboten.androidApp.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import app.saboten.androidUi.buttons.FilledButton
import app.saboten.androidUi.dialogs.BasicDialog
import app.saboten.androidUi.styles.surfaceOver
import app.saboten.androidUi.utils.getActivity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.presentation.GlobalAppViewModel

@Composable
@Destination
fun AppInitializeFailedDialog(
    navigator: DestinationsNavigator,
    globalAppViewModel: GlobalAppViewModel
) {

    val activity = LocalContext.current.getActivity()

    Box(modifier = Modifier.fillMaxSize()) {
        BasicDialog(
            properties = DialogProperties(
                dismissOnClickOutside = false
            ),
            onDismissRequest = {
                navigator.popBackStack()
            },
            title = "네트워크 오류",
            message = "네트워크 오류가 발생했습니다.\n인터넷 연결을 확인해주세요.",
            positiveButton = {
                FilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        globalAppViewModel.retry()
                        navigator.popBackStack()
                    },
                    text = "확인"
                )
            },
            negativeButton = {
                FilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.surfaceOver,
                    onClick = { activity?.finishAndRemoveTask() },
                    text = "앱 종료"
                )
            }
        )
    }
}