package app.saboten.androidUi.utils

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.sabotenShadow(): Modifier = composed {
    shadow(
        2.dp,
        shape = MaterialTheme.shapes.medium,
        ambientColor = Color.Black.copy(0.2f),
        spotColor = Color.Black.copy(0.1f)
    )
}