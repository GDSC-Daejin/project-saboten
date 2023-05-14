package app.saboten.androidApp.ui.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.DragBar
import app.saboten.androidUi.buttons.FilledButton
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.SabotenColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import commonClient.domain.entity.post.CategoryState
import commonClient.domain.entity.post.CategoryType
import commonClient.domain.entity.post.HotPostSortState
import commonClient.domain.entity.post.PeriodState
import commonClient.domain.entity.post.PeriodType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalLayoutApi::class)
@Composable
@Destination
fun SortHotPostDialog(
    initSortState: HotPostSortState,
    resultNavigator: ResultBackNavigator<String>,
) {

    var hotPostSortState by remember {
        mutableStateOf(initSortState)
    }

    val categoryTypes = remember { CategoryType.values() }
    val periodTypes = remember { PeriodType.values() }

    // TODO: 디테일한 크기 조정 필요.

    BasicScaffold(
        backgroundColor = if (MaterialTheme.colors.isLight) Color.White else MaterialTheme.colors.background,
        topBar = {
            BasicTopBar(
                backgroundColor = Color.Transparent,
                title = { Text(text = "뜨거웠던 고민거리 찾기") },
                navigationIcon = {
                    IconButton(onClick = {
                        resultNavigator.navigateBack(Json.encodeToString(initSortState))
                    }) {
                        Icon(imageVector = Icons.Rounded.Close, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            FilledButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .navigationBarsPadding(),
                text = "적용하기",
                onClick = { resultNavigator.navigateBack(Json.encodeToString(hotPostSortState)) }
            )
        }
    ) {

        LazyVerticalGrid(
            GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
            contentPadding = PaddingValues(10.dp)
        ) {
            item(span = { GridItemSpan(2) }) {
                Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "기간",
                        style = MaterialTheme.typography.subtitle1
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
            }

            items(periodTypes) { periodType ->
                PeriodTypeItem(
                    modifier = Modifier.padding(10.dp).clickable {
                        hotPostSortState = hotPostSortState.copy(periodState = PeriodState(periodType))
                    },
                    sortTypeState = hotPostSortState.periodState,
                    sortType = periodType,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item(span = { GridItemSpan(2) }) {
                Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "카테고리",
                        style = MaterialTheme.typography.subtitle1
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            items(categoryTypes) { categoryType ->
                CategoryTypeItem(
                    modifier = Modifier.padding(10.dp).clickable {
                        hotPostSortState = hotPostSortState.copy(categoryState = CategoryState(categoryType))
                    },
                    sortTypeState = hotPostSortState.categoryState,
                    sortType = categoryType,
                )
            }

        }
    }

}

@Composable
fun CategoryTypeItem(
    modifier: Modifier = Modifier,
    sortTypeState: CategoryState,
    sortType: CategoryType,
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (sortTypeState.type == sortType) SabotenColors.green500 else MaterialTheme.colors.onBackground.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = sortType.text,
            color = if (sortTypeState.type == sortType) SabotenColors.green500 else MaterialTheme.colors.onBackground.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun PeriodTypeItem(
    modifier: Modifier = Modifier,
    sortTypeState: PeriodState,
    sortType: PeriodType,
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (sortTypeState.type == sortType) SabotenColors.green500 else MaterialTheme.colors.onBackground.copy(alpha = 0.2f),
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = sortType.text,
            color = if (sortTypeState.type == sortType) SabotenColors.green500 else MaterialTheme.colors.onBackground.copy(alpha = 0.2f)
        )
    }
}