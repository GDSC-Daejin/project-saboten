package app.saboten.androidApp.ui.screens.main.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.saboten.androidApp.extensions.extract
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidApp.ui.list.CategoryItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.MainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import common.model.reseponse.category.Category
import commonClient.data.LoadState
import commonClient.presentation.AppViewModel

@Composable
@Destination
fun SearchScreen(
    navigator: DestinationsNavigator
) {
    SearchScreenContent()
}

@Composable
private fun SearchScreenContent() {

//    val (state, effect, event) = vm.extract()

    BasicScaffold(
        topBar = {
            BasicTopBar(title = {
                Text("Search")
            })
        }
    ) {
//        CategoryGridList(
//            onClick = {
//
//            },
//            modifier = Modifier.fillMaxWidth(),
//            categories = state.categories,
//        )
    }
}

@Composable
private fun CategoryGridList(
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
    categories: List<Category>,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(4)
    ) {
        items(categories, key = { it.id }) {
            CategoryItem(it, onClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryContentPreview() {
    MainTheme {

    }
}