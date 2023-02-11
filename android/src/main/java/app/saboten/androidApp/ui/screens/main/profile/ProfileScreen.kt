package app.saboten.androidApp.ui.screens.main.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import app.saboten.androidApp.ui.destinations.SettingsScreenDestination
import app.saboten.androidApp.ui.providers.LocalMeInfo
import app.saboten.androidApp.ui.providers.MeInfo
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.buttons.SmallOutlinedButton
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.lists.BasicListItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.data.LoadState
import commonClient.domain.entity.user.UserInfo
import commonClient.presentation.main.ProfileScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@Destination
fun ProfileScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<ProfileScreenViewModel>()
    ProfileScreenContent(
        viewModel = viewModel,
        onOpenSettings = { navigator.navigate(SettingsScreenDestination()) }
    )
}


@Composable
private fun ProfileScreenContent(
    viewModel: ProfileScreenViewModel,
    onOpenSettings: () -> Unit,
) {

    val state by viewModel.collectAsState()

    BasicScaffold(
        topBar = {
            BasicTopBar(
                title = {
                    Text(text = "내 프로필")
                },
                actions = {
                    // Setting Icons
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Rounded.Settings, null)
                    }
                }
            )
        }
    ) {

        val items = state.items.collectAsLazyPagingItems()

        LazyColumn(
            modifier = Modifier.padding(it),
        ) {
            item {

                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = MaterialTheme.colors.surface,
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        ProfileBannerUi()

                        LazyRow(
                            contentPadding = PaddingValues(20.dp),
                        ) {

                            items(state.categories.getDataOrNull() ?: emptyList()) { item ->
                                val itemId = if (item.id < 0) null else item.id
                                Surface(
                                    onClick = { viewModel.selectCategory(itemId) },
                                    color = if (state.selectedCategoryId == itemId) MaterialTheme.colors.secondary
                                    else Color.Transparent,
                                    contentColor = if (state.selectedCategoryId == itemId) MaterialTheme.colors.onSecondary
                                    else MaterialTheme.colors.onBackground.copy(0.3f),
                                    shape = CircleShape,
                                    border = if (state.selectedCategoryId == itemId) null
                                    else {
                                        BorderStroke(1.dp, MaterialTheme.colors.onBackground.copy(0.3f))
                                    },
                                ) {

                                    Text(
                                        text = item.name,
                                        modifier = Modifier.padding(10.dp),
                                        color = if (state.selectedCategoryId == itemId) MaterialTheme.colors.onSecondary
                                        else MaterialTheme.colors.onBackground.copy(0.3f),
                                    )

                                }
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }

                    }

                }
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            items(items) {
                it?.let { post ->
                    LargePostCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        post = post,
                        onClicked = {

                        },
                        onCommentClicked = {

                        },
                        onVoteClicked = { vote -> viewModel.requestVote(post.id, vote.id) },
                        onScrapClicked = { viewModel.requestScrap(post.id) },
                        onLikeClicked = { viewModel.requestLike(post.id) },
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            item {
                Spacer(modifier = Modifier.height(100.dp))
                Spacer(modifier = Modifier.navigationBarsPadding())
            }

        }

    }


}

@Composable
private fun ProfileBannerUi() {

    val meInfo = LocalMeInfo.current

    if (meInfo.needLogin) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = "로그인이 필요합니다.",
                style = MaterialTheme.typography.h6,
            )

            Spacer(modifier = Modifier.height(20.dp))

            SmallOutlinedButton(
                text = "로그인",
                onClick = {

                }
            )

        }

    } else {

        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    NetworkImage(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape),
                        meInfo.notNullUserInfo.profilePhotoUrl
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Column {
                        Text(meInfo.notNullUserInfo.nickname, style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            meInfo.notNullUserInfo.email,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(ContentAlpha.medium)
                        )
                    }

                }

            }

        }


    }

}