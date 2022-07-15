package app.saboten.androidApp.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.image.NetworkImage


@Composable
fun CommentItem(
    type: Boolean,
    text: String
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        if (type) {
            Row(modifier = Modifier.align(Alignment.TopStart)) {
                NetworkImage(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    "https://picsum.photos/200"
                )

                CommentText(
                    backgroundColor = MaterialTheme.colors.onPrimary,
                    text = text
                )
            }
        } else {
            Row(modifier = Modifier.align(Alignment.TopEnd)) {

                CommentText(
                    modifier = Modifier.weight(1f),
                    backgroundColor = MaterialTheme.colors.primary,
                    text = text
                )

                NetworkImage(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape),
                    "https://picsum.photos/200"
                )
            }
        }
    }
}

@Composable
fun CommentText(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    text: String
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(5.dp),
        backgroundColor = backgroundColor,
        elevation = 2.dp,
    ) {
        Column(modifier = Modifier.padding(10.dp)) {

            Row(verticalAlignment = Alignment.Bottom) {
                Text(text = "안드로이드")

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = "(android)",
                    style = MaterialTheme.typography.caption,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = text,
                fontWeight = FontWeight.Light
            )

        }
    }
}