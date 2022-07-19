package app.saboten.androidApp.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun KeywordItem(
    keyword: String? = null,
    onClick: (String) -> Unit
) {
    Surface(modifier = Modifier
        .clip(RoundedCornerShape(20.dp))
        .clickable { keyword?.let { onClick(it) } },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
    ) {
        if (keyword != null) {
            Text(
                text = keyword,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                color = MaterialTheme.colors.primary,
                maxLines = 1
            )
        }
    }
}