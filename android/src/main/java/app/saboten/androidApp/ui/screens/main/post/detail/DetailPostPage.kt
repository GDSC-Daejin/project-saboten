package app.saboten.androidApp.ui.screens.main.post.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.providers.LocalMeInfo
import app.saboten.androidApp.ui.screens.LocalOpenLoginDialogEffect
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.domain.entity.post.Post
import commonClient.presentation.main.DetailPostScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@Destination
fun DetailPostScreen(
    postId: Long,
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<DetailPostScreenViewModel>()

    LaunchedEffect(true) {
        viewModel.loadPost(postId)
    }

    DetailPostPageContent(viewModel) {
        navigator.popBackStack()
    }
}

@Composable
fun DetailPostPageContent(
    viewModel: DetailPostScreenViewModel,
    onBackPressed: () -> Unit,
) {

    val state by viewModel.collectAsState()

    val meState = LocalMeInfo.current
    val openLoginDialog = LocalOpenLoginDialogEffect.current

    Scaffold(
        topBar = {
            BasicTopBar(title = { /*TODO*/ }, navigationIcon = {
                ToolbarBackButton(onBackPressed)
            })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            val post = state.post.getDataOrNull()
            if (post != null) {
                LargePostCard(
                    post = post,
                    onClicked = { /*TODO*/ },
                    onVoteClicked = {
                        if (meState.needLogin) openLoginDialog()
                        else viewModel.requestVote(post.id, it.id)
                    },
                    onScrapClicked = {
                        if (meState.needLogin) openLoginDialog()
                        else viewModel.requestScrap(post.id)
                    },
                    onLikeClicked = {
                        if (meState.needLogin) openLoginDialog()
                        else viewModel.requestLike(post.id)
                    }) {

                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            // TODO: 댓글 부분 추가
        }
    }

}