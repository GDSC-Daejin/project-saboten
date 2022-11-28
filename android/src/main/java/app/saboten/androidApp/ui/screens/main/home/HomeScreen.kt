package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.list.PostFeedListItem
import app.saboten.androidApp.ui.screens.main.MainTopBar
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.HeaderBar
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Post
import commonClient.domain.entity.post.Vote
import commonClient.domain.entity.post.VoteColors
import commonClient.domain.entity.user.User

@Composable
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    HomeScreenContent(
        navigator = navigator
    )
}

@Composable
fun HomeScreenContent(
    navigator: DestinationsNavigator
) {

    BasicScaffold(
        topBar = {
            MainTopBar()
        }
    ) {

        LazyColumn(modifier = Modifier.padding(it)) {

            item { HomeScreenTrendingCategories() }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Spacer(modifier = Modifier.height(80.dp))
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }

        }

    }

}