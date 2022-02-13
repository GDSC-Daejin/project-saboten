package app.saboten.androidUiSamples.screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.saboten.androidUi.bars.ToolBar
import app.saboten.androidUi.bars.ToolbarBackButton
import com.google.accompanist.insets.ui.Scaffold

@Composable
fun ListsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            ToolBar(
                title = {
                    Text("Lists")
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