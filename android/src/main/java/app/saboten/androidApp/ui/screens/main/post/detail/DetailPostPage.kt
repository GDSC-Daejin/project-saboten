package app.saboten.androidApp.ui.screens.main.post.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidApp.ui.providers.LocalMeInfo
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.presentation.post.DetailPostScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import app.saboten.androidApp.extensions.toAfterCreatedAt
import app.saboten.androidApp.extensions.toDuration
import app.saboten.androidApp.ui.providers.MeInfo
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.styles.SabotenColors
import commonClient.presentation.post.DetailPostScreenEffect
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDateTime

@Composable
@Destination
fun DetailPostScreen(
    postId: Long,
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<DetailPostScreenViewModel>()

    val meState = LocalMeInfo.current

    LaunchedEffect(meState.needLogin) {
        viewModel.loadPost(postId)
    }

    DetailPostPageContent(viewModel, meState) {
        navigator.popBackStack()
    }
}

@Composable
fun DetailPostPageContent(
    viewModel: DetailPostScreenViewModel,
    meState: MeInfo,
    onBackPressed: () -> Unit,
) {

    val state by viewModel.collectAsState()

    var query by remember { mutableStateOf("") }
    var isPostingComment by remember { mutableStateOf(false) }

    val post = remember(state.post) { state.post.getDataOrNull() }

    val comments = state.comments.collectAsLazyPagingItems()

    viewModel.collectSideEffect {
        when (it) {
            is DetailPostScreenEffect.CommentPosted -> {
                isPostingComment = false
                query = ""
                viewModel.refreshComment()
            }

            is DetailPostScreenEffect.CommentPosting -> {
                isPostingComment = true
            }

            is DetailPostScreenEffect.CommentPostFailed -> {
                isPostingComment = false
            }
        }
    }

    Scaffold(
        topBar = {
            BasicTopBar(title = { /*TODO*/ }, navigationIcon = {
                ToolbarBackButton(onBackPressed)
            })
        },
        bottomBar = {
            if (post != null && meState.needLogin.not()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                        .navigationBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    NetworkImage(
                        url = meState.notNullUserInfo.profilePhotoUrl,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 8.dp))

                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(0.5f)),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .padding(horizontal = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            BasicTextField(
                                modifier = Modifier.weight(1f),
                                value = query,
                                cursorBrush = SolidColor(MaterialTheme.colors.secondary),
                                textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                                onValueChange = { query = it },
                                keyboardActions = KeyboardActions { viewModel.postComment(post.id, query) },
                                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            if (isPostingComment) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    color = MaterialTheme.colors.secondary
                                )
                            } else {
                                IconButton(
                                    modifier = Modifier.size(24.dp),
                                    onClick = { viewModel.postComment(post.id, query) }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Send,
                                        contentDescription = "보내기",
                                        tint = MaterialTheme.colors.onBackground.copy(0.5f)
                                    )
                                }
                            }

                        }
                    }


                }
            }
        }
    ) {
        if (post != null) {
            LazyColumn(
                modifier = Modifier.padding(it),
                content = {
                    item {
                        LargePostCard(
                            post = post,
                            onClicked = { /*TODO*/ },
                            onVoteClicked = {
                                viewModel.requestVote(post.id, it.id)
                            },
                            onScrapClicked = {
                                viewModel.requestScrap(post.id)
                            },
                            onLikeClicked = {
                                viewModel.requestLike(post.id)
                            }) {

                        }
                    }

                    items(comments) { comment ->
                        if (comment != null) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp)
                            ) {
                                NetworkImage(
                                    url = comment.author.profilePhotoUrl,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                                Column {
                                    Text(
                                        comment.author.nickname,
                                        fontSize = 10.sp,
                                        color = SabotenColors.grey400
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        comment.text,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        LocalDateTime.parse(comment.createdAt).toDuration().toAfterCreatedAt(),
                                        fontSize = 10.sp,
                                        color = SabotenColors.grey400
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            )


        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }

        }
    }

}