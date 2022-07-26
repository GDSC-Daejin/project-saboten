package app.saboten.androidApp.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.image.NetworkImage
import common.model.reseponse.category.CategoryResponse

@Composable
fun CategoryItem(
    categoryResponse: CategoryResponse,
    onClick: (CategoryResponse) -> Unit
) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(10.dp)
            .clickable { onClick(categoryResponse) },
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface.copy(alpha = 0.1f)),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 40.dp, vertical = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NetworkImage(
                modifier = Modifier
                    .size(26.dp)
                ,
                url = categoryResponse.iconUrl,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = categoryResponse.name)
        }
    }
}