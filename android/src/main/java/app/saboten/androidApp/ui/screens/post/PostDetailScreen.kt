package app.saboten.androidApp.ui.screens.post

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidApp.ui.list.PostSelectItemIconButton
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.MainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun PostDetailScreen(
    navigator: DestinationsNavigator
) {
    BasicScaffold(
        topBar = {
            BasicTopBar(
                title = {
                    Text("PostDetail")
                },
                navigationIcon = {
                    ToolbarBackButton {
                        navigator.popBackStack()
                    }
                }
            )
        }
    ) {
        PostDetailContent()
    }
}

@Preview(showBackground = true)
@Composable
fun PostDetailScreenPreview() {
    MainTheme {
        PostDetailContent()
    }
}

@Composable
private fun PostDetailContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column {

            PostDetailProfile()

            Spacer(modifier = Modifier.height(25.dp))

            PostDetailVoteContent()

            Spacer(modifier = Modifier.height(23.dp))

            PostDetailVoteIndicator()

            Spacer(modifier = Modifier.height(34.dp))

            PostDetailDivider()

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colors.onSurface.copy(
                        alpha = 0.8f
                    )
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.BarChart,
                        contentDescription = "bar chart"
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Text(
                        text = "자세한 득표 결과보기",
                        style = MaterialTheme.typography.caption
                    )
                }
            }

            PostDetailDivider()
        }
    }
}

@Composable
private fun PostDetailDivider() {
    Divider(
        thickness = 0.5.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
    )
}


@Composable
private fun PostDetailProfile() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        NetworkImage(
            modifier = Modifier
                .size(25.dp)
                .clip(CircleShape)
                .border(
                    border = BorderStroke(width = 2.dp, color = Color(0xFF53654C)),
                    shape = CircleShape
                ),
            "https://picsum.photos/200"
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "안 드루이드",
                    style = MaterialTheme.typography.caption
                )
                Text(
                    text = "(Android)",
                    fontSize = 7.sp,
                    modifier = Modifier.padding(start = 5.dp)
                )
            }

            Text(
                text = "24분전",
                fontSize = 9.sp,
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        PostSelectItemIconButton(
            onClicked = { /*TODO*/ },
            icon = Icons.Rounded.FavoriteBorder,
            iconDescription = "favorite post"
        )

        PostSelectItemIconButton(
            onClicked = { /*TODO*/ },
            icon = Icons.Rounded.MoreHoriz,
            iconDescription = "more action"
        )
    }
}

@Composable
private fun PostDetailVoteContent(
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Box(modifier = modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(top = 50.dp)) {
            Box {
                Button(
                    shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFFA66FEA)),
                    modifier = Modifier
                        .width(screenWidth * 0.5f)
                        .height(screenWidth * 0.3f)
                        .padding(start = 15.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "파인애플 피자",
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                Button(
                    onClick = {  },
                    enabled = false,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomEnd)
                        .size(28.dp),
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 13.dp,
                        bottomStart = 20.dp
                    ),
                    colors = ButtonDefaults.buttonColors(disabledBackgroundColor = MaterialTheme.colors.primary),
                    border = BorderStroke(1.5.dp, Color.White),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "A",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(5.dp))

            Box {
                Button(
                    shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF859DFC)),
                    modifier = Modifier
                        .width(screenWidth * 0.5f)
                        .height(screenWidth * 0.3f)
                        .padding(end = 15.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "민트 초코 아이스크림",
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                Button(
                    onClick = {  },
                    enabled = false,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomStart)
                        .size(28.dp),
                    shape = RoundedCornerShape(
                        topStart = 13.dp,
                        topEnd = 20.dp,
                        bottomEnd = 20.dp
                    ),
                    colors = ButtonDefaults.buttonColors(disabledBackgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)),
                    border = BorderStroke(1.5.dp, Color.White),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "B",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 15.dp),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomEnd = 10.dp, bottomStart = 10.dp),
            border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.2f)),
            elevation = 4.dp
        ) {
            Text(
                text = "무인도에 떨어졌는데 음식 둘 중 하나만 먹어야 된다면?",
                modifier = Modifier
                    .padding(15.dp)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }
    }
}

@Composable
private fun PostDetailVoteIndicator(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "55%",
            style = MaterialTheme.typography.body2
        )

        Spacer(modifier = Modifier.width(15.dp))

        LinearProgressIndicator(
            modifier = Modifier
                .height(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .align(Alignment.CenterVertically),
            progress = 0.55f,
            backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.08f)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            text = "45%",
            style = MaterialTheme.typography.body2
        )
    }
}