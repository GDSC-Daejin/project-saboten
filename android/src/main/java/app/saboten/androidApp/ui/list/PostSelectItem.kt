package app.saboten.androidApp.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chat
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.styles.MainTheme
import commonClient.domain.entity.post.Post

@Composable
fun PostSelectItem(
    modifier: Modifier = Modifier,
    post: Post,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 4.dp,
        modifier = modifier.padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Column {
                Row(modifier = Modifier.fillMaxWidth()) {
                    NetworkImage(
                        modifier = Modifier
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
                        Row(verticalAlignment = Alignment.Bottom) {
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
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = post.text,
                    style = MaterialTheme.typography.subtitle2
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(115.dp)
                        .clip(RoundedCornerShape(20.dp))
                ) {
                    PostSelectItemLeftSelection(
                        topic = "하와이안 피자",
                        count = 35
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    PostSelectItemRightSelection(
                        topic = "민트초코 아이스크림",
                        count = 65
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        PostSelectItemCategory(category = "음식")

                        Spacer(modifier = Modifier.width(10.dp))

                        PostSelectItemCategory(category = "취향")
                    }

                    Row {
                        PostSelectItemIconButton(
                            onClicked = { /*TODO*/ },
                            icon = Icons.Rounded.FavoriteBorder,
                            iconDescription = "Favorite"
                        )
                        PostSelectItemIconButton(
                            onClicked = { /*TODO*/ },
                            icon = Icons.Rounded.Chat,
                            iconDescription = "Chat"
                        )
                        PostSelectItemIconButton(
                            onClicked = { /*TODO*/ },
                            icon = Icons.Rounded.MoreHoriz,
                            iconDescription = "More Horiz"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PostSelectItemLeftSelection(topic: String, count: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.35f)
            .fillMaxHeight()
            .background(
                color = Color(0xFFA66FEA),
                shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
            )
            .clickable { /*TODO*/ }
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = topic,
            color = Color.White,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "${count}%",
            color = Color.White,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
private fun PostSelectItemRightSelection(topic: String, count: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color(0xFF8DA4FF),
                shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp)
            )
            .clickable { /*TODO*/ }
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = topic,
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "${count}%",
            color = Color.White,
            style = MaterialTheme.typography.body2
        )
    }
}

@Composable
fun PostSelectItemIconButton(
    onClicked: () -> Unit,
    icon: ImageVector,
    iconDescription: String
) {
    IconButton(
        onClick = { onClicked },
        modifier = Modifier.then(Modifier.size(36.dp))
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconDescription,
            tint = Color(0xFF969696)
        )
    }
}

@Composable
private fun PostSelectItemCategory(
    category: String
) {
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = "#${category}",
            modifier = Modifier.padding(start = 20.dp, top = 5.dp, end = 20.dp, bottom = 5.dp),
            style = MaterialTheme.typography.caption
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostSelectItemPreview() {
    MainTheme {

    }
}