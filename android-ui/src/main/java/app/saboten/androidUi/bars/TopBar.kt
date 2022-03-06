package app.saboten.androidUi.bars

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar

@Composable
fun ToolbarBackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.Rounded.ArrowBack, null)
    }
}

@Composable
fun BasicTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = rememberInsetsPaddingValues(LocalWindowInsets.current.statusBars),
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 0.dp,
) {
    TopAppBar(
        title, modifier, contentPadding, navigationIcon, actions, backgroundColor, contentColor, elevation
    )
}