package app.saboten.androidUi.buttons

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DEFAULT_SMALL_BUTTON_HEIGHT = 36.dp

@Composable
fun SmallOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
    backgroundColor: Color = Color.Unspecified,
    icon: ImageVector? = null,
    text: String
) {
    OutlinedButton(
        onClick, modifier.height(DEFAULT_SMALL_BUTTON_HEIGHT), enabled,
        elevation = null,
        shape = shape,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor),
        content = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                icon?.let {
                    Icon(imageVector = icon, contentDescription = text)
                    Spacer(modifier = Modifier.width(20.dp))
                }
                Text(
                    text,
                    style = MaterialTheme.typography.button.copy(fontSize = 12.sp),
                    color = MaterialTheme.colors.onSurface.copy(0.5f)
                )
            }
        }
    )
}