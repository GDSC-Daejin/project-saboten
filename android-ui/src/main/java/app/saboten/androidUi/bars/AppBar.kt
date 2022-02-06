package app.saboten.androidUi.bars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToolbarBackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.Rounded.ArrowBack, null)
    }
}

@Composable
fun ToolBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor)
) {
    TopAppBar(
        title, modifier, navigationIcon, actions, backgroundColor, contentColor, 0.dp
    )
}