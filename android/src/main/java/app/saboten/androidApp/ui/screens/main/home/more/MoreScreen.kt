package app.saboten.androidApp.ui.screens.main.home.more

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.BasicTopBar
import com.ramcosta.composedestinations.annotation.Destination
import commonClient.presentation.main.MoreScreenType
import commonClient.presentation.main.MoreScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import app.saboten.androidApp.ui.destinations.DetailPostScreenDestination
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import app.saboten.androidUi.bars.ToolbarBackButton
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

enum class MoreScreenOption {
    HOT,
    RECENT,
    MY_SELECTED,
    MY_SCRAPPED,
}

@Composable
@Destination
fun MoreScreen(
    option: MoreScreenOption,
    navigator: DestinationsNavigator,
) {

    val viewModel = koinViewModel<MoreScreenViewModel>()

    val state by viewModel.collectAsState()

    LaunchedEffect(true) {
        viewModel.setType(
            when (option) {
                MoreScreenOption.HOT -> MoreScreenType.HOT
                MoreScreenOption.RECENT -> MoreScreenType.RECENT
                MoreScreenOption.MY_SELECTED -> MoreScreenType.MY_SELECTED
                MoreScreenOption.MY_SCRAPPED -> MoreScreenType.MY_SCRAPPED
            }
        )
    }

    Scaffold(topBar = {
        BasicTopBar(title = {
            Text(
                text = when (option) {
                    MoreScreenOption.HOT -> "뜨거웠던 고민거리"
                    MoreScreenOption.RECENT -> "최근 고민거리"
                    MoreScreenOption.MY_SELECTED -> "내가 선택했던 글"
                    MoreScreenOption.MY_SCRAPPED -> "내가 스크랩한 글"
                }
            )
        },
            navigationIcon = {
                ToolbarBackButton {
                    navigator.popBackStack()
                }
            }
        )
    }) { padding ->

        val items = state.items.collectAsLazyPagingItems()

        LazyColumn(modifier = Modifier.padding(padding), contentPadding = PaddingValues(20.dp)) {
            items(items, key = { it.id }) { post ->
                val observableCache by viewModel.updatedPostCache.collectAsState()
                val cachedPost = observableCache.firstOrNull { post?.id == it.id } ?: post
                cachedPost?.let { nonNullPost ->
                    LargePostCard(
                        modifier = Modifier.fillMaxWidth(),
                        post = nonNullPost,
                        onClicked = {
                            navigator.navigate(DetailPostScreenDestination(nonNullPost.id))
                        },
                        onCommentClicked = {
                            navigator.navigate(DetailPostScreenDestination(nonNullPost.id))
                        },
                        onVoteClicked = { vote -> viewModel.requestVote(nonNullPost.id, vote.id) },
                        onScrapClicked = { viewModel.requestScrap(nonNullPost.id) },
                        onLikeClicked = { viewModel.requestLike(nonNullPost.id) },
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }

    }

}