package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.saboten.androidApp.extensions.extract
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.lists.FeedSelectItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.presentation.HomeScreenViewModel
import commonClient.presentation.HomeScreenViewModelDelegate

@Composable
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    HomeScreenContent()
}

@Composable
fun HomeScreenContent(
    vm: HomeScreenViewModelDelegate = hiltViewModel<HomeScreenViewModel>()
) {

    val (state, effect, event) = vm.extract()

    BasicScaffold(
        topBar = {
            BasicTopBar(
                title = {
                    Text("Home")
                }
            )
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