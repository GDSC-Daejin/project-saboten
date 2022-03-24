package app.saboten.androidUiSamples.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton

@Composable
fun ListsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            BasicTopBar(
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