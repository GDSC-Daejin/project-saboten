package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.extensions.extract
import app.saboten.androidApp.ui.destinations.PostDetailScreenDestination
import app.saboten.androidApp.ui.list.PostSelectItem
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.utils.shimmer
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.data.LoadState
import commonClient.domain.entity.post.Category
import commonClient.presentation.HomeScreenViewModelDelegate
import kotlinx.coroutines.launch

@Composable
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    HomeScreenContent(
//        hiltViewModel<HomeScreenViewModel>()
        fakeHomeScreenViewModel(),
        navigator = navigator
    )
}

@Composable
fun HomeScreenContent(
    vm: HomeScreenViewModelDelegate,
    navigator: DestinationsNavigator
) {

    val (state, effect, event) = vm.extract()

    val pagerState = rememberPagerState()

    BasicScaffold(
        topBar = {
            Column {
                BasicTopBar(
                    title = {
                        Text("Home")
                    }
                )
                HomeCategoryTab(
                    pagerState = pagerState,
                    categoriesState = state.categoriesState,
                )
            }
        }
    ) {

        HomeFeedPage(
            modifier = Modifier.padding(it),
            pagerState = pagerState,
            categoriesState = state.categoriesState,
            navigator = navigator
        )

    }

}

@Composable
private fun HomeCategoryTab(
    pagerState: PagerState,
    categoriesState: LoadState<List<Category>>
) {
    val coroutineScope = rememberCoroutineScope()
    when (categoriesState) {
        is LoadState.Failed -> {

        }
        is LoadState.Loading -> {
            Surface(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)) {
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
                edgePadding = 20.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                selectedTabIndex = pagerState.currentPage,
                indicator = {},
                divider = {}
            ) {
                categoriesState.data.forEachIndexed { index, category ->
                    val selected = pagerState.currentPage == index
                    Tab(
                        selected,
                        modifier = Modifier
                            .height(36.dp)
                            .width(100.dp)
                            .padding(end = 10.dp)
                            .clip(RoundedCornerShape(100.dp))
                            .background( if (selected) {
                                MaterialTheme.colors.primary
                            } else {
                                MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
                            }),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = MaterialTheme.colors.onPrimary,
                        unselectedContentColor = MaterialTheme.colors.onSurface
                    ) {
                        Text(
                            text = category.name,
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeFeedPage(
    modifier: Modifier,
    pagerState: PagerState,
    categoriesState: LoadState<List<Category>>,
    navigator: DestinationsNavigator
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

                LazyColumn {

                    items(10) {
                        PostSelectItem(
                            text = "무인도에 떨어졌는데 둘 중 하나만 먹을 수 있다면?",
                            onClicked = { navigator.navigate(PostDetailScreenDestination) }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                        Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                    }
                }

            }
        }
    }
}