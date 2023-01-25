package app.saboten.androidApp.ui.screens.main.category

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.styles.SabotenColors
import commonClient.domain.entity.post.HotPostSortState

@Composable
fun SortSelector(
    hotPostSortState: HotPostSortState,
    onExpandMoreClick: () -> Unit = { }
) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .height(26.dp)
            .clip(RoundedCornerShape(5.dp))
            .border(
                width = 2.dp,
                color = SabotenColors.green500,
                shape = RoundedCornerShape(5.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.padding(start = 5.dp))

            Text(
                text = "${hotPostSortState.categoryState.type.text} ", fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SabotenColors.green500
            )
            Text(
                text = "중에서 ", fontSize = 12.sp,
                color = SabotenColors.green500
            )
            Text(
                text = "${hotPostSortState.periodState.type.text} ", fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SabotenColors.green500
            )
            Icon(
                imageVector = Icons.Rounded.ExpandMore,
                contentDescription = "expandMore",
                modifier = Modifier
                    .size(12.dp)
                    .clickable {
                        onExpandMoreClick()
                    },
                tint = SabotenColors.green500
            )
            Spacer(modifier = Modifier.padding(end = 3.dp))
        }
    }
}