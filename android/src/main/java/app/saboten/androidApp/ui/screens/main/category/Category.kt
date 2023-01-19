package app.saboten.androidApp.ui.screens.soopeachtest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.image.NetworkImage
import app.saboten.androidUi.image.SabotenIconPack
import app.saboten.androidUi.image.sabotenIconPack.ABIcon
import commonClient.domain.entity.post.Category

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .width(170.dp)
            .height(160.dp),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Box(
//          modifier =  Modifier.background(
//                brush = category.brush,
//                shape = RoundedCornerShape(15.dp)
//            )
        ) {
            NetworkImage(
                modifier = Modifier,
                url =
                category.iconUrl
            )
            Image(
                SabotenIconPack.ABIcon,
                "Exclude",
                modifier = Modifier.padding(top = 14.dp, start = 10.dp)
            )
            Text(
                text = category.name,
                modifier = Modifier.padding(top = 122.dp, start = 10.dp)
            )
        }
    }
}
