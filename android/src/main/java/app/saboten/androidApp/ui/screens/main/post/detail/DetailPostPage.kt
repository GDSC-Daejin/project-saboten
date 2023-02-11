package app.saboten.androidApp.ui.screens.main.post.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.domain.entity.post.Post
import commonClient.presentation.main.DetailPostScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@Destination
fun DetailPostScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = koinViewModel<DetailPostScreenViewModel>()

    DetailPostPageContent(viewModel)
}

@Composable
fun DetailPostPageContent(
    viewModel: DetailPostScreenViewModel
) {

    val state by viewModel.collectAsState()

    Column() {

        LargePostCard(
            post = state.post?: Post.loadingPost,
            onClicked = { /*TODO*/ },
            onVoteClicked = { viewModel.selectVote(it.id)},
            onScrapClicked = { viewModel.scrapPost() },
            onLikeClicked = { viewModel.likePost() }) {

        }

        // TODO: 댓글 부분 추가
    }
}