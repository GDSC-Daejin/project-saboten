package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.remember
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
import commonClient.data.LoadState
import commonClient.domain.entity.post.Post
import commonClient.presentation.main.HomeScreenState

@Composable
fun HomeScreenTrendingItems(
    state : HomeScreenState
) {

    val banner = remember(state) { state.banners }

    val pagerState = rememberPagerState()

    when (banner) {
        is LoadState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.96f)
            ) {

            }
        }
        is LoadState.Success -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.96f)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                SabotenColors.green500,
                                SabotenColors.green800
                            )
                        )
                    )
            ) {

                HorizontalPager(
                    state = pagerState,
                    count = banner.data.size,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val item = banner.data[it]
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    ),
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.h4,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = item.text,
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
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
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.96f)
            ) {
            }
        }
    }

}