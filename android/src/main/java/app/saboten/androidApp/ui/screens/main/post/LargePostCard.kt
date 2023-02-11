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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidApp.ui.screens.main.post.vote.SelectedVoteItem
import app.saboten.androidApp.ui.screens.main.post.vote.UnSelectedVoteItem
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.styles.SabotenColors
import app.saboten.androidUi.utils.sabotenShadow
import commonClient.domain.entity.post.Post
import commonClient.domain.entity.post.Vote

@Composable
fun LargePostCard(
    modifier : Modifier = Modifier,
    post: Post,
    onClicked: () -> Unit,
    onVoteClicked : (Vote) -> Unit,
    onScrapClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .sabotenShadow()
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.medium,
            )
            .clickable(onClick = onClicked)
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
                        url = "https://picsum.photos/200/200",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape)
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
                    contentDescription = "스크랩"
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

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            val sum = remember(post) { post.voteResponses.sumOf { it.count } }

            if (post.selectedVote != null) {
                SelectedVoteItem(
                    vote = post.voteResponses.first(),
                    onVoteItemClicked = { onVoteClicked(post.voteResponses.first()) },
                    isFirst = true,
                    isSelected = post.selectedVote == 0L,
                    sum = sum
                )

                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                SelectedVoteItem(
                    vote = post.voteResponses[1],
                    onVoteItemClicked = { onVoteClicked(post.voteResponses[1]) },
                    isFirst = false,
                    isSelected = post.selectedVote == 1L,
                    sum = sum
                )
            } else {
                UnSelectedVoteItem(
                    post.voteResponses.first()
                ) { onVoteClicked(post.voteResponses.first()) }

                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                UnSelectedVoteItem(
                    post.voteResponses[1]
                ) { onVoteClicked(post.voteResponses[1]) }
            }

            Spacer(modifier = Modifier.padding(vertical = 9.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(post.categories) {
                        GroupItem(text = it.name)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
