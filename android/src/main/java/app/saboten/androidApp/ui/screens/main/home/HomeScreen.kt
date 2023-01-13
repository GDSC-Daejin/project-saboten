package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.list.CategoryItem
import app.saboten.androidApp.ui.list.PostFeedListItem
import app.saboten.androidApp.ui.list.PostSelectItem
import app.saboten.androidApp.ui.screens.main.MainTopBar
import app.saboten.androidUi.bars.HeaderBar
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.presentation.main.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator,
) {

    val vm = koinViewModel<HomeScreenViewModel>()

    HomeScreenContent(
        vm = vm,
        navigator = navigator
    )
}

@Composable
fun HomeScreenContent(
    vm: HomeScreenViewModel,
    navigator: DestinationsNavigator,
) {

    val state by vm.collectAsState()

    val lazyListState = rememberLazyListState()

    val isHeaderScrolled by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemScrollOffset > 0
        }
    }

    val isLight = MaterialTheme.colors.isLight

    val isStatusBarIconColorDark by remember(isHeaderScrolled) {
        derivedStateOf {
            isLight && isHeaderScrolled
        }
    }

    val backgroundColor by animateColorAsState(targetValue = if (isHeaderScrolled) MaterialTheme.colors.surface else Color.Transparent)
    val contentColor by animateColorAsState(targetValue = if (isHeaderScrolled) MaterialTheme.colors.primary else Color.White)

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(isStatusBarIconColorDark) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = isStatusBarIconColorDark
        )
    }

    DisposableEffect(true) {
        onDispose {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = isLight
            )
        }
    }

    BasicScaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = lazyListState
            ) {

                item { HomeScreenTrendingItems(state) }

                item { HeaderBar(title = "뜨거웠던 고민거리") }

                state.hotPost.getDataOrNull()?.first()?.let {
                    item {
                        PostSelectItem(post = it) {

                        }

                    }
                }

                item {
                    Spacer(modifier = Modifier.height(36.dp))
                    HeaderBar(title = "실시간 인기 카테고리")
                }

                state.trendingCategories.getDataOrNull()?.let { categories ->
                    item {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(categories) { category ->
                                CategoryItem(category = category) {

                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(36.dp))
                    HeaderBar(title = "최근 고민거리")
                }

                state.recentPost.getDataOrNull()?.let { post ->
                    item {
                        PostFeedListItem(post = post) {

                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(36.dp))
                    HeaderBar(title = "내가 선택했던 글")
                }

                state.selectedPost.getDataOrNull()?.let { post ->
                    item {
                        PostSelectItem(post = post) {

                        }
                    }
                }


                item {
                    Spacer(modifier = Modifier.height(36.dp))
                    HeaderBar(title = "내가 스크랩한 글")
                }

                state.scrappedPosts.getDataOrNull()?.let { posts ->
                    items(posts) { post ->
                        PostFeedListItem(post = post) {

                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(36.dp))
                }

                item {
                    Spacer(modifier = Modifier.navigationBarsPadding())
                }

            }

            MainTopBar(
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )

        }
    }
}
