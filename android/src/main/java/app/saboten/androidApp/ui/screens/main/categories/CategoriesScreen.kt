package app.saboten.androidApp.ui.screens.main.categories

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.extensions.extract
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.lists.CategoryItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.MainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import common.model.reseponse.category.Category
import commonClient.data.LoadState
import commonClient.presentation.AppViewModel

@Composable
@Destination
fun CategoriesScreen(
    navigator: DestinationsNavigator,
    appViewModel: AppViewModel
) {

    val (appState, appEffect, appEvent) = appViewModel.extract()

    BasicScaffold(
        topBar = {
            BasicTopBar(title = {
                Text("Categories")
            })
        }
    ) {
        CategoryGridList(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth(),
            categoriesState = appState.categoriesState,
        )
    }
}

@Composable
fun CategoryGridList(
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
    categoriesState: LoadState<List<Category>>,
) {
    when (categoriesState) {
        is LoadState.Failed -> {

        }
        is LoadState.Loading -> {

        }
        is LoadState.Success -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(4)
            ) {
                items(categoriesState.data, key = { it.id }) {
                    CategoryItem(
                        text = it.name,
                        icon = Icons.Rounded.QuestionMark
                    ) { onClick(it) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryContentPreview() {
    MainTheme {

    }
}