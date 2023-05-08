package app.saboten.androidApp.ui.screens.main.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import app.saboten.androidApp.R
import app.saboten.androidApp.ui.destinations.DetailPostScreenDestination
import app.saboten.androidApp.ui.screens.main.post.LargePostCard
import app.saboten.androidApp.ui.screens.main.search.card.RankSection
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import commonClient.presentation.main.SearchScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.orbitmvi.orbit.compose.collectAsState

@Composable
@Destination
fun SearchScreen(
    navigator: DestinationsNavigator,
) {

    val viewModel = koinViewModel<SearchScreenViewModel>()

    val state by viewModel.collectAsState()

    var query by remember { mutableStateOf("") }

    BasicScaffold(
        topBar = {
            BasicTopBar(title = { Text("검색") })
        }
    ) { padding ->

        val items = state.items.collectAsLazyPagingItems()

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .navigationBarsPadding(), contentPadding = PaddingValues(20.dp)
        ) {

            stickyHeader {
                Surface(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = MaterialTheme.colors.background),
                    color = MaterialTheme.colors.surface,
                    border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            value = query,
                            cursorBrush = SolidColor(MaterialTheme.colors.secondary),
                            textStyle = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.onSurface),
                            onValueChange = { query = it },
                            keyboardActions = KeyboardActions {
                                viewModel.search(query)
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = androidx.compose.ui.text.input.ImeAction.Search)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        if (query.isNotEmpty()) {
                            IconButton(
                                modifier = Modifier.size(24.dp),
                                onClick = { query = "" }) {
                                Icon(
                                    imageVector = Icons.Rounded.Cancel,
                                    contentDescription = "지우기",
                                    tint = MaterialTheme.colors.onBackground.copy(0.5f)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        IconButton(
                            modifier = Modifier.size(24.dp),
                            onClick = { viewModel.search(query) }) {
                            Icon(
                                imageVector = Icons.Rounded.Search,
                                contentDescription = "검색",
                                tint = MaterialTheme.colors.secondary
                            )
                        }
                    }
                }

                if (query.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colors.background)
                            .padding(top = 10.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.align(Alignment.CenterStart),
                                text = "최근 검색어",
                                style = MaterialTheme.typography.subtitle2
                            )
                            TextButton(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                onClick = { viewModel.clearSearchHistory() }
                            ) {
                                Text(text = "전체 삭제", style = MaterialTheme.typography.caption)
                            }
                        }
                        LazyRow {
                            state.searchHistories.forEach {
                                item {
                                    Surface(
                                        shape = CircleShape,
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colors.onBackground.copy(0.1f)
                                        ),
                                        onClick = {
                                            query = it
                                            viewModel.search(query)
                                        },
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(
                                                vertical = 4.dp,
                                                horizontal = 8.dp
                                            ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(text = it)
                                            Icon(
                                                imageVector = Icons.Rounded.Close,
                                                contentDescription = "삭제",
                                                modifier = Modifier
                                                    .size(20.dp)
                                                    .clip(CircleShape)
                                                    .clickable {
                                                        viewModel.removeSearchHistory(it)
                                                    }
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                }
                            }
                        }
                        Divider()
                    }
                }
            }

            item {
                if (query.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 2.dp, end = 2.dp, bottom = 20.dp)
                    ) {

                        // TODO: 서버에서 인기 주제 가져오기
                        RankSection(
                            SectionTitle = "실시간 인기 주제", contentList =
                            listOf(
                                "똥구멍에 이빨이 난다면",
                                "당신은 고친놈인가 감친놈인가",
                                "100억 받고 똥먹기 vs 100억 받고 똥먹기",
                            )
                        )

                        // TODO: 서버에서 인기 카테고리 가져오기
                        RankSection(
                            SectionTitle = "실시간 인기 카테고리", contentList =
                            listOf(
                                "음식",
                                "연애",
                                "MBTI",
                            )
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

            }

            if (
                state.totalCount != null
                && state.lastSearchedQuery != null
                && query == state.lastSearchedQuery
            ) {

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {

                        Text(
                            text = "'${state.lastSearchedQuery}'",
                            style = MaterialTheme.typography.subtitle1,
                            color = MaterialTheme.colors.secondary
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        if (state.totalCount == 0L) {
                            Text(
                                text = "찾을 수가 없었어요...",
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.onBackground.copy(0.5f)
                            )
                        } else {
                            Text(
                                text = "총 ${state.totalCount}건을 찾았어요!",
                                style = MaterialTheme.typography.subtitle1,
                                color = MaterialTheme.colors.onBackground.copy(0.5f)
                            )
                        }

                    }
                }

                if (state.totalCount == 0L) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                modifier = Modifier.size(200.dp),
                                painter = painterResource(R.drawable.not_found_image),
                                contentDescription = "검색된 항목이 없을 때 나타나는 이미지"
                            )
                        }
                    }
                } else {
                    items(items, key = { it.id }) { post ->
                        val observableCache by viewModel.updatedPostCache.collectAsState()
                        val cachedPost = observableCache.firstOrNull { post?.id == it.id } ?: post
                        cachedPost?.let { nonNullPost ->
                            LargePostCard(
                                modifier = Modifier.fillMaxWidth(),
                                post = nonNullPost,
                                onClicked = { navigator.navigate(DetailPostScreenDestination(postId = nonNullPost.id)) },
                                onCommentClicked = {

                                },
                                onVoteClicked = { vote ->
                                    viewModel.requestVote(
                                        nonNullPost.id,
                                        vote.id
                                    )
                                },
                                onScrapClicked = { viewModel.requestScrap(nonNullPost.id) },
                                onLikeClicked = { viewModel.requestLike(nonNullPost.id) },
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }


            }

        }


    }

}