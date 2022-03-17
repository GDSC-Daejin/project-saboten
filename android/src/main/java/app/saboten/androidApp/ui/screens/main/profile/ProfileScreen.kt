package app.saboten.androidApp.ui.screens.main.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.buttons.SmallOutlinedButton
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.lists.BasicListItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.google.accompanist.insets.navigationBarsHeight
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
@Destination
fun ProfileScreen(
    navigator: DestinationsNavigator
) {

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val pages = remember {
        listOf(
            "쓴 글",
            "선택한 글",
            "댓글 남긴 글",
        )
    }

    BasicScaffold(
        topBar = {
            Surface {
                Column(modifier = Modifier.statusBarsPadding()) {

                    ProfileBannerUi()

                    TabRow(
                        modifier = Modifier.height(56.dp),
                        selectedTabIndex = pagerState.currentPage,
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                            )
                        }
                    ) {
                        pages.forEachIndexed { index, title ->
                            Tab(
                                text = { Text(title) },
                                selected = pagerState.currentPage == index,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                            )
                        }
                    }
                }
            }
        }
    ) {
        HorizontalPager(
            pages.size,
            state = pagerState,
            modifier = Modifier.padding(it)
        ) {
            LazyColumn {
                items(100) {
                    BasicListItem(
                        title = "Item $it",
                        subtitle = "Subtitle $it",
                        leftSideUi = {

                        }
                    ) {

                    }
                }
                item {
                    Spacer(modifier = Modifier.navigationBarsHeight(20.dp))
                }
            }
        }

    }


}

@Composable
private fun ProfileBannerUi() {

    Surface(modifier = Modifier.fillMaxWidth()) {

        Box(modifier = Modifier.padding(20.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                NetworkImage(
                    modifier = Modifier.size(56.dp).clip(CircleShape),
                    "https://picsum.photos/200"
                )

                Spacer(modifier = Modifier.width(20.dp))

                Column {
                    Text("Name", style = MaterialTheme.typography.h6)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "@username",
                        style = MaterialTheme.typography.body2,
                        color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                    )
                }

            }

            SmallOutlinedButton({}, text = "프로필 수정", modifier = Modifier.align(Alignment.CenterEnd))

        }

    }

}