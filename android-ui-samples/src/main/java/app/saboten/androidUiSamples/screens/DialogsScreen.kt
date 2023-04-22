package app.saboten.androidUiSamples.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton
import app.saboten.androidUi.buttons.FilledButton
import app.saboten.androidUi.dialogs.BasicDialog
import app.saboten.androidUi.dialogs.DialogContentGravity
import app.saboten.androidUi.styles.surfaceOver

enum class Types {
    NoContent,
    WithContentBottom,
    WithContentTop,
}

@Composable
fun DialogsScreen(navController: NavController) {

    var showDialog by remember { mutableStateOf<Types?>(null) }

    Scaffold(
        topBar = {
            BasicTopBar(
                title = {
                    Text("Dialogs")
                },
                navigationIcon = {
                    ToolbarBackButton {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {

            Column(modifier = Modifier.padding(20.dp)) {
                FilledButton({
                    showDialog = Types.NoContent
                }, text = "컨텐트가 없는 다이얼로그")

                Spacer(Modifier.height(10.dp))

                FilledButton({
                    showDialog = Types.WithContentBottom
                }, text = "컨텐트가 아래에 있는 다이얼로그")

                Spacer(Modifier.height(10.dp))

                FilledButton({
                    showDialog = Types.WithContentTop
                }, text = "컨텐트가 위에 있는 다이얼로그")

            }

        }
    }

    if (showDialog != null) {
        BasicDialog(
            onDismissRequest = {
                showDialog = null
            },
            title = "안녕하세요",
            message = "저는 해리입니다.\n이건 다이얼로그 창이예요.",
            positiveButton = {
                FilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { showDialog = null },
                    text = "확인"
                )
            },
            dialogContentGravity = if (showDialog == Types.WithContentBottom) DialogContentGravity.Bottom else DialogContentGravity.Top,
            content = showDialog?.takeIf { it != Types.NoContent }?.run {
                {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colors.surfaceOver)
                    )
                }
            },
            negativeButton = {
                FilledButton(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colors.surfaceOver,
                    onClick = { showDialog = null },
                    text = "취소"
                )
            }
        )
    }

}