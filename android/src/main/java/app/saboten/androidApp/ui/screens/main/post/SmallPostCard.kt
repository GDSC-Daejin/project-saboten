package app.saboten.androidApp.ui.screens.main.post

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.styles.SabotenColors
import commonClient.domain.entity.post.Post

@Composable
fun SmallPostCard(
    post: Post,
    onScrapClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .width(320.dp)
            .wrapContentHeight()
            .shadow(4.dp, shape = RoundedCornerShape(10.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            )

    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    NetworkImage(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape),
                        url = post.author.profilePhotoUrl
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 4.dp))
                    Column() {
                        Text(
                            post.author.nickname,
                            fontSize = 12.sp
                        )
                        Text(
                            "24분전",
                            fontSize = 10.sp,
                            color = SabotenColors.grey200
                        )
                    }
                }

                Icon(
                    modifier = Modifier
                        .size(34.dp)
                        .clickable {
                            onScrapClicked()
                        },
                    imageVector = Icons.Rounded.Bookmark,
                    tint =
                    if (post.isScraped == true) SabotenColors.green500 else SabotenColors.grey200,
                    contentDescription = "북마크"
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 9.dp))

            Text(
                text = post.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.padding(vertical = 9.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row() {
                    GroupItem("음식")
                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                    GroupItem("취향")
                }

                Row() {
                    Icon(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(26.dp)
                            .clickable {
                                onLikeClicked()
                            },
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "하트",
                        tint =
                        if (post.isLiked == true) SabotenColors.green500 else SabotenColors.grey200
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 6.dp))
                    Icon(
                        modifier = Modifier
                            .padding(2.dp)
                            .size(26.dp)
                            .clickable {
                                onCommentClicked()
                            },
                        imageVector = Icons.Rounded.Forum,
                        contentDescription = "댓글",
                        tint = SabotenColors.grey200
                    )
                }
            }
        }
    }
}
