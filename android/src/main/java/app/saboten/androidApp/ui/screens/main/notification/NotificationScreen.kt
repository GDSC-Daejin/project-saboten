package app.saboten.androidApp.ui.screens.main.notification

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
fun NotificationScreen(
    navigator: DestinationsNavigator
) {


    BasicScaffold(
        topBar = {
            BasicTopBar(title = {
                Text("Notification")
            })
        }
    ) {


    }


}