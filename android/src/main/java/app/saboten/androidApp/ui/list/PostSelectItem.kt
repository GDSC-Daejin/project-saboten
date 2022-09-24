package app.saboten.androidApp.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.image.NetworkImage
import com.google.accompanist.flowlayout.FlowRow
import commonClient.domain.entity.post.Post

@Composable
fun PostSelectItem(
    modifier: Modifier = Modifier,
    post: Post,
    onClicked: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = 0.dp,
        modifier = modifier.fillMaxWidth(),
        onClick = onClicked
    ) {
        Column(modifier = Modifier.padding(20.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                NetworkImage(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    post.author.profilePhotoUrl
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(post.author.nickname, style = MaterialTheme.typography.body1)

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(post.text, style = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(20.dp))

            FlowRow(
                crossAxisSpacing = 6.dp,
                mainAxisSpacing = 6.dp,
            ) {
                post.categories.forEach {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colors.onSurface.copy(0.05f),
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(10.dp, 6.dp)
                    ) {
                        Text(
                            text = "#${it.name}",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(0.5f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            val sum = remember(post) { post.voteResponses.sumOf { it.count } }

            post.voteResponses.sortedByDescending { it.count }
                .forEachIndexed { index, voteResponse ->

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = voteResponse.topic,
                            style = MaterialTheme.typography.body1,
                            color = if (index == 0) MaterialTheme.colors.primary
                            else MaterialTheme.colors.onSurface.copy(0.5f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        LinearProgressIndicator(
                            progress = voteResponse.count.toFloat() / sum,
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(1f),
                            color = if (index == 0) MaterialTheme.colors.primary
                            else MaterialTheme.colors.onSurface.copy(0.1f),
                            backgroundColor = MaterialTheme.colors.onSurface.copy(0.05f),
                        )

                        Text(
                            text = "${
                                String.format(
                                    "%.2f",
                                    (voteResponse.count.toFloat() / sum * 100)
                                )
                            }%",
                            modifier = Modifier.width(50.dp),
                            style = MaterialTheme.typography.caption,
                            color = if (index == 0) MaterialTheme.colors.primary
                            else MaterialTheme.colors.onSurface.copy(0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }

        }

    }
}


@Composable
fun PostFeedListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onClicked: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(0.05f)),
        elevation = 0.dp,
        modifier = modifier
            .width(300.dp)
            .heightIn(min = 200.dp),
        onClick = onClicked
    ) {
        Column(modifier = Modifier.padding(20.dp)) {


            Row(verticalAlignment = Alignment.CenterVertically) {

                NetworkImage(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape),
                    post.author.profilePhotoUrl
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(post.author.nickname, style = MaterialTheme.typography.body1)

            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(post.text, style = MaterialTheme.typography.h4)

            Spacer(modifier = Modifier.height(20.dp))

            FlowRow(
                crossAxisSpacing = 6.dp,
                mainAxisSpacing = 6.dp,
            ) {
                post.categories.forEach {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colors.onSurface.copy(0.05f),
                                shape = RoundedCornerShape(100.dp)
                            )
                            .padding(10.dp, 6.dp)
                    ) {
                        Text(
                            text = "#${it.name}",
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(0.5f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            val sum = remember(post) { post.voteResponses.sumOf { it.count } }

            post.voteResponses.sortedByDescending { it.count }
                .forEachIndexed { index, voteResponse ->

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = voteResponse.topic,
                            style = MaterialTheme.typography.body1,
                            color = if (index == 0) MaterialTheme.colors.primary
                            else MaterialTheme.colors.onSurface.copy(0.5f)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        LinearProgressIndicator(
                            progress = voteResponse.count.toFloat() / sum,
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(1f),
                            color = if (index == 0) MaterialTheme.colors.primary
                            else MaterialTheme.colors.onSurface.copy(0.1f),
                            backgroundColor = MaterialTheme.colors.onSurface.copy(0.05f),
                        )

                        Text(
                            text = "${
                                String.format(
                                    "%.2f",
                                    (voteResponse.count.toFloat() / sum * 100)
                                )
                            }%",
                            modifier = Modifier.width(50.dp),
                            style = MaterialTheme.typography.caption,
                            color = if (index == 0) MaterialTheme.colors.primary
                            else MaterialTheme.colors.onSurface.copy(0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }

        }

    }
}