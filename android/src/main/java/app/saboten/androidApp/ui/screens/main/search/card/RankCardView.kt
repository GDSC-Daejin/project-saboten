package app.saboten.androidApp.ui.screens.main.search.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.styles.SabotenColors

@Composable
fun RankCardView(
    content: String,
    rank: Int,
    elevation: Dp = 1.dp) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(69.dp),
        elevation = elevation,
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 14.dp,
                    bottom = 16.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "${rank}위",
                style = MaterialTheme.typography.caption,
                color = SabotenColors.green500
            )
            Text(
                text = content,
                style = MaterialTheme.typography.body1,
                color = SabotenColors.grey1000
            )

        }
    }
}