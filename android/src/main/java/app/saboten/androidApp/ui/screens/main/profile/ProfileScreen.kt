package app.saboten.androidApp.ui.screens.main.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import app.saboten.androidApp.ui.destinations.DetailPostScreenDestination
import app.saboten.androidApp.ui.destinations.SettingsScreenDestination
import app.saboten.androidApp.ui.providers.LocalMeInfo
import app.saboten.androidApp.ui.screens.LocalOpenLoginDialogEffect
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.image.sabotenlogo.Google
import app.saboten.androidUi.image.sabotenlogo.SabotenIcons
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.SabotenColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.domain.entity.post.Post
import commonClient.domain.entity.user.MyPageCount
import commonClient.presentation.main.ProfileScreenViewModel
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
        onPostClicked = { navigator.navigate(DetailPostScreenDestination(postId = it.id)) },
        onOpenSettings = { navigator.navigate(SettingsScreenDestination()) }
    )
}


@Composable
private fun ProfileScreenContent(
    viewModel: ProfileScreenViewModel,
    onPostClicked: (Post) -> Unit,
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

        val items = state.myPosts.collectAsLazyPagingItems()

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

                        ProfileBannerUi(viewModel)

                        PostInfoBox(state.myPageCount.getDataOrNull())

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
                        onClicked = { onPostClicked(it) },
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
private fun ProfileBannerUi(
    viewModel: ProfileScreenViewModel
) {

    val meInfo = LocalMeInfo.current

    val openLoginDialog = LocalOpenLoginDialogEffect.current

    if (meInfo.needLogin) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openLoginDialog() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = SabotenIcons.Google,
                    contentDescription = "구글 로고",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.padding(start = 4.dp))
                Text(
                    text = "로그인",
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = "로그인",
                )
            }

            Spacer(modifier = Modifier.padding(top = 4.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "로그인 후 더 많은 서비스를 이용해보세요",
                fontSize = 12.sp,
                color = SabotenColors.grey500
            )

        }

    } else {

        LaunchedEffect(key1 = true ) {
            viewModel.loadMyPage()
        }

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

@Composable
private fun PostInfoBox(counts: MyPageCount? = null) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(90.dp)
            .border(
                width = 1.dp,
                color = SabotenColors.grey200,
                shape = RoundedCornerShape(10.dp)
            ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (counts != null) {
            TextWithCount("게시글", counts.myPost)
            TextWithCount("투표", counts.votedPost)
            TextWithCount("스크랩", counts.scrapedPost)
        } else {
            TextWithCount("게시글")
            TextWithCount("투표")
            TextWithCount("스크랩")
        }


    }
}

@Composable
private fun TextWithCount(text: String, count: Long? = null) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (count != null) {
            Text(
                text = "$count",
                fontSize = 20.sp,
                color = SabotenColors.grey900
            )
        } else {
            Icon(
                imageVector = Icons.Outlined.Lock, contentDescription = "자물쇠",
                tint = SabotenColors.grey600
            )
        }

        Spacer(modifier = Modifier.padding(top = 10.dp))
        Text(
            text = text,
            fontSize = 10.sp,
            color = SabotenColors.grey600
        )
    }
}