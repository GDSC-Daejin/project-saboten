package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.lists.BasicListItem
import app.saboten.androidUi.lists.FeedSelectItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    BasicScaffold(
        topBar = {
            BasicTopBar(title = {
                Text("Home")
            })
        }
    ) {

        LazyColumn(modifier = Modifier.padding(it)) {
            items(10) {
                FeedSelectItem()
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }
        }

    }

}