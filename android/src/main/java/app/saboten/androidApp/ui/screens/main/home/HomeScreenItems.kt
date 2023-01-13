package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.HeaderBar
import app.saboten.androidUi.styles.SabotenColors
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import commonClient.domain.entity.post.Post
import commonClient.presentation.main.HomeScreenState

@Composable
fun HomeScreenTrendingItems(
    state : HomeScreenState
) {
    val pagerState = rememberPagerState()

    // TODO 실제 데이터로 변경 필요함
    val colors = listOf(
        listOf(Color(0xFF2BC0E4), Color(0xFFD2E7CA)),
        listOf(Color(0xFF2EC781), Color(0xFF00733C)),
        listOf(Color(0xFF3A85FF), Color(0xFF0062FF)),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.96f)
    ) {

        HorizontalPager(
            state = pagerState,
            count = colors.size,
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors[it]))
            )

        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        ) {

            Text(
                modifier = Modifier
                    .background(SabotenColors.grey1000.copy(0.3f), RoundedCornerShape(100.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                textAlign = TextAlign.Center,
                text = "${pagerState.currentPage + 1} / ${pagerState.pageCount}",
                style = MaterialTheme.typography.body2,
                color = SabotenColors.grey100
            )

        }

    }

}