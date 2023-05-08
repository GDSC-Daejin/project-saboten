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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.styles.SabotenColors

@Composable
fun TopRankCardView(
    content: String,
    elevation: Dp = 1.dp
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = SabotenColors.green400,
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp),
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
                text = "1위",
                style = MaterialTheme.typography.caption,
                color = Color.White
            )
            Text(
                text = content,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    letterSpacing = 0.15.sp
                ),
                color = Color.White
            )

        }
    }
}