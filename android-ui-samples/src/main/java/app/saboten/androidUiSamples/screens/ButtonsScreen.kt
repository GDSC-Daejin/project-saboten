package app.saboten.androidUiSamples.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.saboten.androidUi.bars.ToolBar
import app.saboten.androidUi.bars.ToolbarBackButton
import app.saboten.androidUi.buttons.BottomButtonBar
import app.saboten.androidUi.buttons.FilledButton
import app.saboten.androidUi.styles.surfaceOver

@Composable
fun ButtonsScreen(navController: NavController) {

    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text("Buttons")
                },
                navigationIcon = {
                    ToolbarBackButton {
                        navController.popBackStack()
                    }
                }
            )
        },
        bottomBar = {
            BottomButtonBar(
                rightButtonRatio = 0.7f,
                leftButton = {
                    FilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "취소",
                        backgroundColor = MaterialTheme.colors.surfaceOver,
                        onClick = {
                            navController.popBackStack()
                        }
                    )
                },
                rightButton = {
                    FilledButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = "확인",
                        onClick = {
                            navController.popBackStack()
                        }
                    )
                }
            )
        }
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            FilledButton({}, text = "FilledButton")

            Spacer(Modifier.height(10.dp))

            FilledButton({}, text = "FilledButton with Icon", icon = Icons.Rounded.AddAPhoto)

            Spacer(Modifier.height(10.dp))

        }
    }

}