package app.saboten.androidApp.ui.screens.main.post.vote

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.styles.SabotenColors
import commonClient.domain.entity.post.Vote
import commonClient.utils.calculatePercent

@Composable
fun SelectedVoteItem(
    vote: Vote,
    onVoteItemClicked: () -> Unit,
    isFirst: Boolean = false,
    isSelected: Boolean,
    sum: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onVoteItemClicked() }
            .background(
                color = if (isSelected) SabotenColors.green500 else Color.White.copy(0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (isSelected) SabotenColors.green500 else SabotenColors.green200,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(top = 14.dp)
                .size(18.dp)
                .clip(CircleShape)
                .background(if (isSelected) Color.White else SabotenColors.grey200),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (isFirst) "A" else "B",
                fontSize = 12.sp,
                color =
                if (isSelected) SabotenColors.green500 else Color.White
            )
        }
        Text(
            text = calculatePercent(vote.count.toFloat(), sum),
            fontSize = 24.sp,
            color = if (isSelected) Color.White else SabotenColors.green200,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(horizontal = 40.dp),
            text = vote.topic,
            fontSize = 10.sp,
            maxLines = 1,
            color = if (isSelected) Color.White else SabotenColors.grey200,
            overflow = TextOverflow.Ellipsis
        )
    }
}
