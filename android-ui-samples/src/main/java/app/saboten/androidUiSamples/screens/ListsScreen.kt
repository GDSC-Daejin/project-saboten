package app.saboten.androidUiSamples.screens

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ModeNight
import androidx.compose.material.icons.rounded.WbSunny
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import app.saboten.androidUi.bars.ToolBar
import app.saboten.androidUi.bars.ToolbarBackButton

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