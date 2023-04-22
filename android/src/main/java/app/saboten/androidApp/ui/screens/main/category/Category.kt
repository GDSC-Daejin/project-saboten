package app.saboten.androidApp.ui.screens.main.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.image.SabotenIconPack
import app.saboten.androidUi.image.sabotenIconPack.ABIcon
import app.saboten.androidUi.styles.SabotenColors
import commonClient.domain.entity.post.Category

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .width(170.dp)
            .height(160.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick,
        color = MaterialTheme.colors.secondary
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(android.graphics.Color.parseColor(category.startColor)),
                            Color(android.graphics.Color.parseColor(category.endColor))
                        )
                    )
                )
        ) {
            Image(
                SabotenIconPack.ABIcon,
                "Exclude",
                modifier = Modifier
                    .padding(top = 14.dp, start = 10.dp)
                    .align(Alignment.TopStart)
            )
            NetworkImage(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(160.dp),
                url = category.iconUrl,
                shimmer = false
            )
            Text(
                text = category.name,
                modifier = Modifier.padding(top = 122.dp, start = 10.dp)
            )
        }
    }
}
