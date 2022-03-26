package app.saboten.androidUi.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.styles.MainTheme

@Composable
fun CategoryItem(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(10.dp).clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            color = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(5.dp)
        ) {
            Box(modifier = Modifier.size(26.dp)) {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    imageVector = icon,
                    contentDescription = text,
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = text)
    }

}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    MainTheme {
        CategoryItem(
            text = "연애",
            icon = Icons.Default.Favorite,
            onClick = {}
        )
    }
}