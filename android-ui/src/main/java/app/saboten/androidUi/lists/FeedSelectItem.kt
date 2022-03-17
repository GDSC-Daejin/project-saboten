package app.saboten.androidUi.lists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.styles.MainTheme

@Composable
fun FeedSelectItem(
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
            ) {
                NetworkImage(
                    modifier = modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .border(
                            border = BorderStroke(width = 2.dp, color = Color(0xFF53654C)),
                            shape = CircleShape
                        ),
                    "https://picsum.photos/200"
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "헤르만 헤세",
                            style = MaterialTheme.typography.caption
                        )
                        Text(
                            text = "(Herman Hesse)",
                            fontSize = 7.sp,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }

                    Text(
                        text = "24분전",
                        fontSize = 9.sp,
                        modifier = Modifier
                            .padding(top = 5.dp)
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = "Liked Choice",
                        tint = Color(0xFFB7B7B7),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.MoreHoriz,
                        contentDescription = "More Actions",
                        tint = Color(0xFF969696),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                modifier = Modifier.shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                ) {
                    Text(
                        text = "무인도에 떨어졌는데 음식 둘 중 하나만 먹어야 한다면?",
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(15.dp),
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row() {
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(bottomStart = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFA9A9A9))
                            .padding(top = 17.dp, bottom = 17.dp)
                    ) {
                        Text(
                            text = "선택지 제목",
                            color = Color.White,
                            style = MaterialTheme.typography.body1.copy(
                                shadow = Shadow(
                                    color = Color(0x73000000),
                                    offset = Offset(0f, 0f),
                                    blurRadius = 4f
                                )
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                        Text(
                            text = "득표수",
                            color = Color(0xFFC6C6C6),
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(top = 3.dp)
                        )
                    }
                }

                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    shape = RoundedCornerShape(bottomEnd = 20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color(0xFFA9A9A9))
                            .padding(top = 17.dp, bottom = 17.dp)
                    ) {
                        Text(
                            text = "선택지 제목",
                            color = Color.White,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.body1.copy(
                                shadow = Shadow(
                                    color = Color(0x73000000),
                                    offset = Offset(0f, 0f),
                                    blurRadius = 4f
                                )
                            ),
                            modifier = Modifier.fillMaxWidth(1f)
                        )
                        Text(
                            text = "득표수",
                            color = Color(0xFFC6C6C6),
                            style = MaterialTheme.typography.caption,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(top = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FeedSelectItemPreview() {
    MainTheme {
        FeedSelectItem()
    }
}