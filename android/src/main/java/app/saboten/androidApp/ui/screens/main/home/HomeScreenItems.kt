package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.HeaderBar

@Composable
fun HomeScreenTrendingCategories() {
    Column(modifier = Modifier.fillMaxWidth()) {

        HeaderBar("실시간 인기 카테고리")

        LazyRow(
            modifier = Modifier.background(MaterialTheme.colors.surface),
            contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 30.dp)
        ) {
            item {

            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}