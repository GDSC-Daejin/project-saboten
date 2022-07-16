package app.saboten.androidApp.ui.screens.main.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.list.CategoryItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidApp.ui.list.KeywordItem
import com.ramcosta.composedestinations.annotation.Destination
import common.model.reseponse.category.CategoryResponse

@Composable
@Destination
fun SearchScreen(
//    navigator: DestinationsNavigator
) {
    SearchScreenContent()
}

val keywordList = listOf("공부", "ENFP", "무인도", "도시락")

val categoryMock: List<CategoryResponse> = listOf(
    CategoryResponse(id = 0L, "고민", "https://user-images.githubusercontent.com/52291662/179352847-8762c8c3-5f95-45fa-9fcd-8a314a39ad10.png"),
    CategoryResponse(id = 1L, "연애", "https://user-images.githubusercontent.com/52291662/179352865-d818a5b2-a757-4deb-9973-02b5a6b34a46.png"),
    CategoryResponse(id = 2L, "패션", "https://user-images.githubusercontent.com/52291662/179352877-e242d211-d7e5-403b-b868-04820314f142.png"),
    CategoryResponse(id = 3L, "음식", "https://user-images.githubusercontent.com/52291662/179352887-41b058dd-ff84-4fea-9f02-92d532fac776.png"),
    CategoryResponse(id = 4L, "취향", "https://user-images.githubusercontent.com/52291662/179352726-a499d511-68c3-4b0b-bb4d-f5ade54c558a.png"),
    CategoryResponse(id = 5L, "기타", "https://user-images.githubusercontent.com/52291662/179352897-044a526c-7ff8-4b3c-9118-2623d6119de4.png")
)

@Preview(showBackground = true)
@Composable
private fun SearchScreenContent() {

//    val (state, effect, event) = vm.extract()

    BasicScaffold {
        Column(modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
        ) {
            SearchBox(
                onSearch = { }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = "카테고리",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            CategoryGridList(
                onClick = {

                },
                modifier = Modifier.fillMaxWidth(),
//            categories = state.categories,
                categories = categoryMock
            )

            KeywordTab(keywords = keywordList, onClick = {})
        }
    }
}

@Composable
private fun CategoryGridList(
    onClick: (CategoryResponse) -> Unit,
    modifier: Modifier = Modifier,
    categories: List<CategoryResponse>
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(3),
    ) {
        items(categories, key = { it.id }) { category ->
            CategoryItem(category, onClick = { onClick(category) })
        }
    }
}

@Composable
private fun SearchBox(
    onSearch: () -> Unit
) {
    Box(modifier = Modifier.padding(10.dp)) {
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSearch() },
            colors = ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
                contentColor = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colors.onSurface
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "제목이나 설명으로 검색해보세요",
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
private fun KeywordTab(
    keywords: List<String>,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(5.dp),
        elevation = 2.dp,
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 15.dp)
        ) {
            Text(
                text = "요즘의 인기 키워드",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                keywords.forEachIndexed { index, keyword ->
                    KeywordItem(onClick = { onClick(keyword) }, keyword = keyword)

                    if (index != keywords.lastIndex) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
    }
}