package app.saboten.androidApp.ui.screens.main.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.lists.BasicListItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun NotificationsScreen(
    navigator: DestinationsNavigator
) {


    BasicScaffold(
        topBar = {
            BasicTopBar(title = {
                Text("Notifications")
            })
        }
    ) {

        LazyColumn(modifier = Modifier.padding(it)) {
            items(100) {
                BasicListItem(
                    title = "Item $it",
                    subtitle = "Subtitle $it",
                    leftSideUi = {
                        Text("🌵")
                    }
                ) {

                }
            }

            item {
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                Spacer(modifier = Modifier.height(20.dp))
            }
        }


    }


}