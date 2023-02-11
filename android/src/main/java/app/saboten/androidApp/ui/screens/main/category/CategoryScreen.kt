package app.saboten.androidApp.ui.screens.main.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
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
import androidx.paging.compose.itemsIndexed
import app.saboten.androidApp.ui.screens.main.MainTopBar
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import app.saboten.androidUi.utils.sabotenShadow

@Composable
@Destination
fun CategoryScreen(
    navigator: DestinationsNavigator,
) {

    val viewModel = koinViewModel<CategoryScreenViewModel>()

    CategoryScreenContent(viewModel)
}

@Composable
private fun CategoryScreenContent(viewModel: CategoryScreenViewModel) {

    val state by viewModel.collectAsState()

    Scaffold(
        topBar = {
            MainTopBar(
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.secondary,
            )
        },
        content = {

            val items = state.items.collectAsLazyPagingItems()

            LazyColumn(
                modifier = Modifier.padding(it),
            ) {
                stickyHeader {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .sabotenShadow()
                    ) {
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
                                    else MaterialTheme.colors.onBackground,
                                    shape = CircleShape,
                                    border = if (state.selectedCategoryId == itemId) null
                                    else {
                                        BorderStroke(1.dp, MaterialTheme.colors.onBackground)
                                    },
                                ) {

                                    Text(
                                        text = item.name,
                                        modifier = Modifier.padding(10.dp),
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
    )

}
