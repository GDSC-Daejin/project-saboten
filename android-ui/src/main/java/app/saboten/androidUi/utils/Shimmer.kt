package app.saboten.androidUi.utils


import android.annotation.SuppressLint
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.shimmer(
    visible: Boolean = true,
    color: Color = MaterialTheme.colors.onBackground,
    shape: Shape = RoundedCornerShape(100.dp),
    alpha: Float = 0.1f
) =  placeholder(
    visible = visible,
    color = color.copy(alpha),
    shape = shape,
    highlight =
    PlaceholderHighlight.shimmer(highlightColor = color.copy(alpha * 2))
)