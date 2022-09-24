package app.saboten.androidApp.ui.screens.main.category

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.saboten.androidApp.extensions.extract
import app.saboten.androidApp.ui.screens.postlist.PostList
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.utils.shimmer
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.data.LoadState
import commonClient.domain.entity.post.Category
import commonClient.presentation.CategoryScreenViewModel
import commonClient.presentation.CategoryScreenViewModelDelegate
import commonClient.presentation.PagedPostListScreenViewModel
import commonClient.presentation.PagedPostListScreenViewModelDelegate
import kotlinx.coroutines.launch

@Composable
@Destination
fun CategoryScreen(
    navigator: DestinationsNavigator
) {
    CategoryScreenContent(
        hiltViewModel<CategoryScreenViewModel>()
    )
}

@Composable
private fun CategoryScreenContent(
    vm: CategoryScreenViewModelDelegate
) {

    val (state, effect, event) = vm.extract()

    val pagerState = rememberPagerState()

    BasicScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            CategoryTab(pagerState, state.categoriesState)
            CategoryFeedPage(
                pagerState = pagerState,
                categoriesState = state.categoriesState
            )
        }
    }
}

@Composable
private fun CategoryTab(
    pagerState: PagerState,
    categoriesState: LoadState<List<Category>>
) {
    val coroutineScope = rememberCoroutineScope()
    when (categoriesState) {
        is LoadState.Failed -> {

        }
        is LoadState.Loading -> {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState(), enabled = false)
                ) {
                    Spacer(modifier = Modifier.width(20.dp))
                    repeat(8) {
                        Box(
                            modifier = Modifier
                                .height(36.dp)
                                .width(100.dp)
                                .shimmer()
                                .align(Alignment.CenterVertically),
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }
            }
        }
        is LoadState.Success -> {
            ScrollableTabRow(
                backgroundColor = Color.Transparent,
                edgePadding = 0.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    ,
                selectedTabIndex = pagerState.currentPage,
                indicator = {},
                divider = {}
            ) {
                categoriesState.data.forEachIndexed { index, category ->
                    val selected = pagerState.currentPage == index
                    Tab(
                        selected,
                        modifier = Modifier.height(36.dp),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = MaterialTheme.colors.onBackground,
                        unselectedContentColor = MaterialTheme.colors.onBackground.copy(0.1f)
                    ) {
                        Text(
                            text = "#${category.name}",
                            style = MaterialTheme.typography.h4
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryFeedPage(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    categoriesState: LoadState<List<Category>>
) {
    when (categoriesState) {
        is LoadState.Failed -> {

        }
        is LoadState.Loading -> {

        }
        is LoadState.Success -> {
            HorizontalPager(
                modifier = modifier,
                state = pagerState,
                count = categoriesState.data.size
            ) {
                PostList(categoriesState.data[it])
            }
        }
    }
}

