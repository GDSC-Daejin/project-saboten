package app.saboten.androidApp.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.styles.MainTheme
import common.model.reseponse.category.Category

@Composable
fun CategoryItem(
    category: Category,
    onClick: (Category) -> Unit
) {
    Column(
        modifier = Modifier.padding(10.dp).clickable { onClick(category) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            color = MaterialTheme.colors.secondary,
            shape = RoundedCornerShape(5.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                NetworkImage(
                    modifier = Modifier.size(26.dp).align(Alignment.Center),
                    url = category.iconUrl,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = category.name)
    }

}