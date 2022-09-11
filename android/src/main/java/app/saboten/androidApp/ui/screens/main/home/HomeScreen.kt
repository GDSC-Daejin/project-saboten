package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.saboten.androidApp.extensions.extract
import app.saboten.androidApp.ui.list.PostFeedListItem
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
import commonClient.presentation.HomeScreenViewModel
import commonClient.presentation.HomeScreenViewModelDelegate

@Composable
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    HomeScreenContent(
        hiltViewModel<HomeScreenViewModel>(),
//        fakeHomeScreenViewModel(),
        navigator = navigator
    )
}

@Composable
fun HomeScreenContent(
    vm: HomeScreenViewModelDelegate,
    navigator: DestinationsNavigator
) {

    val (state, effect, event) = vm.extract()

    BasicScaffold(
        topBar = {
        }
    ) {

        LazyColumn(modifier = Modifier.padding(it)) {

            item {

                BasicTopBar(
                    title = {
                        Text(text = "선인장")
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            BadgedBox(badge = {
                                Badge()
                            }) {
                                Icon(Icons.Rounded.Notifications, null)
                            }
                        }
                    }
                )

                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.surface)
                        .padding(horizontal = 20.dp)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colors.onSurface.copy(0.03f),
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(15.dp)
                            .clickable {

                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Rounded.Search,
                            null,
                            tint = MaterialTheme.colors.onSurface.copy(0.2f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "찾아보기",
                            style = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.onSurface.copy(0.5f)
                        )
                    }
                }
            }

            item {
                HeaderBar(
                    "주간 베스트 고민거리",
                    "더보기"
                ) {

                }
                LazyRow(
                    modifier = Modifier.background(MaterialTheme.colors.surface),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 30.dp)
                ) {
                    item {
                        repeat(5) {
                            PostFeedListItem(
                                post = Post(
                                    0,
                                    "탕수육 먹을때 찍먹 vs 부먹",
                                    User(0, "Harry", "https://picsum.photos/200/200"),
                                    listOf(
                                        Vote(0, "찍먹", 10, VoteColors.BLUE),
                                        Vote(1, "부먹", 1, VoteColors.RED),
                                    ),
                                    listOf(
                                        Category(0, "호불호", ""),
                                        Category(0, "먹을거", ""),
                                    ),
                                    0,
                                    null,
                                    "",
                                    null
                                )
                            ) {

                            }
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }


            item {
                HeaderBar(
                    "실시간 베스트 고민거리",
                    "더보기"
                ) {

                }
                LazyRow(
                    modifier = Modifier.background(MaterialTheme.colors.surface),
                    contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 30.dp)
                ) {
                    item {
                        repeat(5) {
                            PostFeedListItem(
                                post = Post(
                                    0,
                                    "탕수육 먹을때 찍먹 vs 부먹",
                                    User(0, "Harry", "https://picsum.photos/200/200"),
                                    listOf(
                                        Vote(0, "찍먹", 10, VoteColors.BLUE),
                                        Vote(1, "부먹", 1, VoteColors.RED),
                                    ),
                                    listOf(
                                        Category(0, "호불호", ""),
                                        Category(0, "먹을거", ""),
                                    ),
                                    0,
                                    null,
                                    "",
                                    null
                                )
                            ) {

                            }
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
                Spacer(modifier = Modifier.height(80.dp))
                Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
            }

        }

    }

}