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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.saboten.androidApp.extensions.extract
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidApp.ui.list.PostSelectItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.utils.shimmer
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import common.model.reseponse.category.Category
import commonClient.data.LoadState
import commonClient.presentation.AppViewModel
import commonClient.presentation.AppViewModelDelegate
import commonClient.presentation.HomeScreenViewModel
import commonClient.presentation.HomeScreenViewModelDelegate
import kotlinx.coroutines.launch

@Composable
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator,
    appViewModel: AppViewModel
) {
    HomeScreenContent(
        hiltViewModel<HomeScreenViewModel>(),
        appViewModel
    )
}

@Composable
fun HomeScreenContent(
    vm: HomeScreenViewModelDelegate,
    appVm: AppViewModelDelegate
) {

    val (appState, appEffect, appEvent) = appVm.extract()
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
                    categoriesState = appState.categoriesState,
                )
            }
        }
    ) {

        HomeFeedPage(
            modifier = Modifier.padding(it),
            pagerState = pagerState,
            categoriesState = appState.categoriesState
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
            Surface(modifier = Modifier.fillMaxWidth().height(56.dp)) {
                Row(
                    modifier = Modifier.fillMaxSize()
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
                edgePadding = 20.dp,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    Box(
                        Modifier
                            .pagerTabIndicatorOffset(pagerState, tabPositions)
                            .fillMaxWidth()
                            .height(4.dp)
                            .background(MaterialTheme.colors.secondary, shape = RoundedCornerShape(2.dp)),
                    )
                },
                divider = {}
            ) {
                categoriesState.data.forEachIndexed { index, category ->
                    Tab(
                        pagerState.currentPage == index,
                        modifier = Modifier.height(48.dp).padding(start = 16.dp, end = 16.dp),
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = MaterialTheme.colors.onSurface
                    ) {
                        Text(category.name)
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

                LazyColumn {

                    items(10) {
                        PostSelectItem(text = "무인도에 떨어졌는데 둘 중 하나만 먹을 수 있다면?")
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