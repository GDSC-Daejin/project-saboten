package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.bars.DragBar
import app.saboten.androidUi.styles.SabotenColors
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import commonClient.domain.entity.post.CategoryType
import commonClient.domain.entity.post.PeriodType
import commonClient.domain.entity.post.SortState
import commonClient.domain.entity.post.SortType
import commonClient.domain.entity.post.toHotPostSortState

@Composable
@Destination(style = DestinationStyle.BottomSheet::class)
fun SortHotPostDialog(
    initSortState: String,
    resultNavigator: ResultBackNavigator<String>,
) {

    var hotPostSortState by remember {
        mutableStateOf(initSortState.toHotPostSortState())
    }

    // TODO: 디테일한 크기 조정 필요.
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFAFA))
            .height(400.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DragBar()

        LazyHorizontalGrid(
            modifier = Modifier
                .height(267.dp),
            horizontalArrangement = Arrangement.spacedBy(36.dp),
            rows = GridCells.Fixed(1)
        ) {

            item {
                LazyColumn(
                ) {
                    items(CategoryType.values()) {
                        SortTypeItem(
                            sortTypeState = hotPostSortState.categoryState,
                            sortType = it
                        ) { categoryType ->
                            hotPostSortState = hotPostSortState.copy(
                                categoryState = hotPostSortState.categoryState.copy(type = categoryType)
                            )

                        }
                        Spacer(modifier = Modifier.padding(bottom = 12.dp))
                    }
                }
            }

            item {
                LazyColumn(
                ) {
                    items(PeriodType.values()) {
                        SortTypeItem(
                            sortTypeState = hotPostSortState.periodState,
                            sortType = it
                        ) { periodType ->
                            hotPostSortState = hotPostSortState.copy(
                                periodState = hotPostSortState.periodState.copy(type = periodType)
                            )
                        }
                        Spacer(modifier = Modifier.padding(bottom = 12.dp))
                    }
                }
            }
        }

        Button(
            modifier = Modifier
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color(0xff00C064),
                            Color(0xff20AE6C)
                        )
                    )
                )
                .fillMaxWidth()
                .height(44.dp),
            onClick = {
                resultNavigator.navigateBack(hotPostSortState.toJsonString())
            }) {
            Text(text = "적용하기", fontSize = 16.sp)
        }

    }
}

@Composable
fun <S : SortState, T : SortType> SortTypeItem(
    sortTypeState: S,
    sortType: T,
    onSortTypeItemClick: (T) -> Unit
) {
    Box(
        modifier = Modifier
            .width(144.dp)
            .height(50.dp)
            .clickable {
                onSortTypeItemClick(sortType)
            }
            .border(
                width = 2.dp,
                color = if (sortTypeState.type == sortType) SabotenColors.green500 else SabotenColors.grey200,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = sortType.text,
            color = if (sortTypeState.type == sortType) SabotenColors.green500 else SabotenColors.grey200
        )
    }
}