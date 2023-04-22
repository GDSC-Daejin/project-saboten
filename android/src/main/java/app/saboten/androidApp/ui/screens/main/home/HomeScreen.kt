package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.destinations.CategoryScreenDestination
import app.saboten.androidApp.ui.destinations.DetailPostScreenDestination
import app.saboten.androidApp.ui.destinations.MoreScreenDestination
import app.saboten.androidApp.ui.providers.LocalMeInfo
import app.saboten.androidApp.ui.screens.LocalOpenLoginDialogEffect
import app.saboten.androidApp.ui.screens.main.MainTopBar
import app.saboten.androidApp.ui.screens.main.home.more.MoreScreenOption
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import app.saboten.androidApp.ui.screens.main.post.SmallPostCard
import app.saboten.androidApp.ui.screens.main.category.CategoryItem
import app.saboten.androidUi.bars.HeaderBar
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.domain.entity.post.Post
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
        onCategoryClicked = {
            navigator.navigate(CategoryScreenDestination(initSelectedItemId = it))
        },
        onPostClicked = {
            navigator.navigate(DetailPostScreenDestination(postId = it.id))
        },
        onMorePostClicked = {
            navigator.navigate(MoreScreenDestination(option = it))
        }
    )
}

@Composable
fun HomeScreenContent(
    vm: HomeScreenViewModel,
    onCategoryClicked: (Long) -> Unit = {},
    onPostClicked: (Post) -> Unit = {},
    onMorePostClicked: (MoreScreenOption) -> Unit = {},
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

    val meState = LocalMeInfo.current
    val openLoginDialog = LocalOpenLoginDialogEffect.current

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

                state.hotPost.getDataOrNull()?.let { posts ->
                    item { HeaderBar(title = "뜨거웠던 고민거리") }

                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(),
                            contentPadding = PaddingValues(start = 20.dp, end = 10.dp)
                        ) {
                            items(posts, key = { it.id }) { post ->
                                val observableCache by vm.updatedPostCache.collectAsState()
                                val cachedPost = observableCache.firstOrNull { post.id == it.id } ?: post
                                cachedPost.let { nonNullPost ->
                                    LargePostCard(
                                        modifier = Modifier.width(320.dp),
                                        post = nonNullPost,
                                        onClicked = {
                                            onPostClicked(nonNullPost)
                                        },
                                        onCommentClicked = {
                                            onPostClicked(nonNullPost)
                                        },
                                        onVoteClicked = { vote ->
                                            if (meState.needLogin) openLoginDialog()
                                            else vm.requestVote(nonNullPost.id, vote.id)
                                        },
                                        onLikeClicked = {
                                            if (meState.needLogin) openLoginDialog()
                                            else vm.requestLike(nonNullPost.id)
                                        },
                                        onScrapClicked = {
                                            if (meState.needLogin) openLoginDialog()
                                            else vm.requestScrap(nonNullPost.id)
                                        },
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }

                state.trendingCategories.getDataOrNull()?.let { categories ->

                    item {
                        Spacer(modifier = Modifier.height(36.dp))
                        HeaderBar(title = "실시간 인기 카테고리")
                    }

                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(),
                            contentPadding = PaddingValues(start = 20.dp, end = 10.dp)
                        ) {
                            items(categories) { category ->
                                CategoryItem(category = category) {
                                    onCategoryClicked(category.id)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                            }
                        }
                    }
                }

                state.recentPost.getDataOrNull()?.let { posts ->
                    item {
                        Spacer(modifier = Modifier.height(36.dp))
                        HeaderBar(title = "최근 고민거리", moreButtonText = "더보기", moreButtonAction = {
                            onMorePostClicked(MoreScreenOption.RECENT)
                        })
                    }

                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(),
                            contentPadding = PaddingValues(start = 20.dp, end = 10.dp)
                        ) {
                            items(posts, key = { it.id }) { post ->
                                val observableCache by vm.updatedPostCache.collectAsState()
                                val cachedPost = observableCache.firstOrNull { post.id == it.id } ?: post
                                cachedPost.let { nonNullPost ->
                                    SmallPostCard(
                                        post = nonNullPost,
                                        onClicked = {
                                            onPostClicked(nonNullPost)
                                        },
                                        onLikeClicked = {
                                            if (meState.needLogin) openLoginDialog()
                                            else vm.requestLike(nonNullPost.id)
                                        },
                                        onScrapClicked = {
                                            if (meState.needLogin) openLoginDialog()
                                            else vm.requestScrap(nonNullPost.id)
                                        },
                                        onCommentClicked = {
                                            onPostClicked(nonNullPost)
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }

                state.selectedPost.getDataOrNull()?.let { posts ->

                    item {
                        Spacer(modifier = Modifier.height(36.dp))
                        HeaderBar(title = "내가 선택했던 글", moreButtonText = "더보기", moreButtonAction = {
                            onMorePostClicked(MoreScreenOption.MY_SELECTED)
                        })
                    }

                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(),
                            contentPadding = PaddingValues(start = 20.dp, end = 10.dp)
                        ) {
                            items(posts, key = { it.id }) { post ->
                                val observableCache by vm.updatedPostCache.collectAsState()
                                val cachedPost = observableCache.firstOrNull { post.id == it.id } ?: post
                                cachedPost.let { nonNullPost ->

                                    SmallPostCard(
                                        post = nonNullPost,
                                        onClicked = {
                                            onPostClicked(nonNullPost)
                                        },
                                        onLikeClicked = {
                                            vm.requestLike(nonNullPost.id)
                                        },
                                        onScrapClicked = {
                                            vm.requestScrap(nonNullPost.id)
                                        },
                                        onCommentClicked = {
                                            onPostClicked(nonNullPost)
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                    }
                }

                state.scrappedPosts.getDataOrNull()?.let { posts ->

                    item {
                        Spacer(modifier = Modifier.height(36.dp))
                        HeaderBar(title = "내가 스크랩한 글", moreButtonText = "더보기", moreButtonAction = {
                            onMorePostClicked(MoreScreenOption.MY_SCRAPPED)
                        })
                    }

                    items(posts, key = { it.id }) { post ->
                        val observableCache by vm.updatedPostCache.collectAsState()
                        val cachedPost = observableCache.firstOrNull { post.id == it.id } ?: post
                        LargePostCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            post = cachedPost,
                            onClicked = {
                                onPostClicked(cachedPost)
                            },
                            onCommentClicked = {
                                onPostClicked(cachedPost)
                            },
                            onVoteClicked = { vote -> vm.requestVote(cachedPost.id, vote.id) },
                            onScrapClicked = { vm.requestScrap(cachedPost.id) },
                            onLikeClicked = { vm.requestLike(cachedPost.id) },
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(100.dp))
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
