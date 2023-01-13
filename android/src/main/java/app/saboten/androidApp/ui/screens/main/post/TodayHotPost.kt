package app.saboten.androidApp.ui.screens.main.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import commonClient.domain.entity.post.Post

@Composable
fun TodayHotPost(
    post: Post,
    onFirstVoteClicked: () -> Unit,
    onSecondVoteClicked: () -> Unit,
    onScrapClicked: () -> Unit,
    onLikeClicked: () -> Unit,
    onCommentClicked: () -> Unit
) {
    Spacer(modifier = Modifier.padding(vertical = 15.dp))
    Column() {
        Text(
            text = "오늘의 Hot",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(vertical = 8.dp))

        LargePostCard(
            post = post,
            onFirstVoteClicked = { onFirstVoteClicked() },
            onSecondVoteClicked = { onSecondVoteClicked() },
            onScrapClicked = {
                onScrapClicked()
            },
            onLikeClicked = {
                onLikeClicked()
            },
            onCommentClicked = {
                onCommentClicked()
            }
        )
    }
}
