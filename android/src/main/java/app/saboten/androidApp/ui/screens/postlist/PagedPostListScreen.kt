package app.saboten.androidApp.ui.screens.postlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.*
import app.saboten.androidApp.extensions.extract
import app.saboten.androidApp.ui.list.PostSelectItem
import commonClient.domain.entity.post.Category
import commonClient.domain.entity.post.Post
import commonClient.domain.entity.post.Vote
import commonClient.domain.entity.post.VoteColors
import commonClient.domain.entity.user.User
import commonClient.presentation.PagedPostListScreenViewModel
import commonClient.presentation.PagedPostListScreenViewModelDelegate
import kotlinx.coroutines.flow.Flow

@Composable
fun PostList(
    category: Category,
    vm: PagedPostListScreenViewModelDelegate = hiltViewModel<PagedPostListScreenViewModel>()
) {

    val (state, effect, event) = vm.extract()

    LaunchedEffect(true) {
        event(
            PagedPostListScreenViewModelDelegate.Event
                .LoadPostsWithCategoryId(category.id)
        )
    }

    val list: LazyPagingItems<Post> = state.postPage.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(20.dp)
    ) {

        items(list) {
            if (it != null) {
                PostSelectItem(post = it) {

                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }


}