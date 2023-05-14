package app.saboten.androidApp.ui.screens.main.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.BasicTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.presentation.main.CategoryScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import app.saboten.androidApp.ui.destinations.DetailPostScreenDestination
import app.saboten.androidApp.ui.providers.LocalMeInfo
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import commonClient.domain.entity.post.Post

@Composable
@Destination
fun CategoryScreen(
    initSelectedItemId: Long = 10,
    navigator: DestinationsNavigator,
) {

    val viewModel = koinViewModel<CategoryScreenViewModel>()

    val meState = LocalMeInfo.current

    LaunchedEffect(true) {
        viewModel.initSelectedCategory(initSelectedItemId)
    }

    LaunchedEffect(meState.needLogin){
        viewModel.refreshCurrentCategoryItems()
    }

    CategoryScreenContent(viewModel = viewModel) {
        navigator.navigate(DetailPostScreenDestination(postId = it.id))
    }

}

@Composable
private fun CategoryScreenContent(
    viewModel: CategoryScreenViewModel,
    onPostClicked: (Post) -> Unit,
) {

    val state by viewModel.collectAsState()

    Scaffold(
        topBar = {
//            MainTopBar(
//                backgroundColor = MaterialTheme.colors.surface,
//                contentColor = MaterialTheme.colors.secondary,
//            )
            BasicTopBar(title = { Text("카테고리") })
        },
        content = {

            val items = state.items.collectAsLazyPagingItems()

            LazyColumn(
                modifier = Modifier.padding(it),
            ) {
                stickyHeader {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = MaterialTheme.colors.surface,
                    ) {
                        LazyRow(
                            contentPadding = PaddingValues(20.dp),
                        ) {

                            items(state.categories.getDataOrNull() ?: emptyList()) { item ->
                                val itemId = if (item.id < 0) 10 else item.id
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

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }

                items(items) { post ->
                    val observableCache by viewModel.updatedPostCache.collectAsState()
                    val cachedPost = observableCache.firstOrNull { post?.id == it.id } ?: post
                    cachedPost?.let { nonNullPost ->
                        LargePostCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp),
                            post = nonNullPost,
                            onClicked = { onPostClicked(nonNullPost) },
                            onCommentClicked = {

                            },
                            onVoteClicked = { vote -> viewModel.vote(nonNullPost.id, vote.id) },
                            onScrapClicked = { viewModel.scrap(nonNullPost.id) },
                            onLikeClicked = { viewModel.like(nonNullPost.id) },
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
    )

}
