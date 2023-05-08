package app.saboten.androidApp.ui.screens.main.search.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.styles.SabotenColors

@Composable
fun RankSection(
    SectionTitle: String,
    contentList: List<String>
) {

    Column {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = SectionTitle,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                letterSpacing = 0.15.sp,
                color = SabotenColors.grey1000
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        contentList.forEachIndexed { index, content ->
            if (index == 0) {
                TopRankCardView(content = content)
            } else {
                RankCardView(content = content, rank = index + 1)
            }
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}