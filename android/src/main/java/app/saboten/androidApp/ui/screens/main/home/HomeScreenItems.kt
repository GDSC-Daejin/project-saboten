package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import app.saboten.androidUi.styles.SabotenColors
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import app.saboten.androidUi.image.NetworkImage
import commonClient.data.LoadState
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
            ) {

                HorizontalPager(
                    state = pagerState,
                    pageCount = banner.data.size,
                    modifier = Modifier.fillMaxSize()
                ){ page ->

                    val item = banner.data[page]

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        getColor(banner.data[page].startColor),
                                        getColor(banner.data[page].endColor)
                                    )
                                )
                            )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            NetworkImage(
                                modifier = Modifier
                                    .fillMaxSize(),
                                url = item.iconUrl,
                            )
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(20.dp)
                            ) {
                                Text(
                                    text = item.subtitle,
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxHeight(0.75f)
                                    .align(Alignment.BottomStart)
                                    .padding(20.dp),
                            ) {
                                Text(
                                    text = "오늘의 ${item.category} 고민거리",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.height(1.dp))
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.h4,
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
                            .background(
                                SabotenColors.grey1000.copy(0.3f),
                                RoundedCornerShape(100.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        textAlign = TextAlign.Center,
                        text = "${pagerState.currentPage + 1} / ${banner.data.size}",
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

fun getColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor("#$colorString"))
}