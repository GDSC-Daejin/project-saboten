package app.saboten.androidUiSamples.screens

import com.google.accompanist.insets.ui.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.saboten.androidUi.bars.ToolBar
import app.saboten.androidUi.bars.ToolbarBackButton

@Composable
fun TextFieldsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text("TextFields")
                },
                navigationIcon = {
                    ToolbarBackButton {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {

    }
}