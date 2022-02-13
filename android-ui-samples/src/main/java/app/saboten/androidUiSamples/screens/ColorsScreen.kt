package app.saboten.androidUiSamples.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.MaterialTheme
import com.google.accompanist.insets.ui.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.saboten.androidUi.bars.ToolBar
import app.saboten.androidUi.bars.ToolbarBackButton
import app.saboten.androidUi.styles.surfaceOver

@Composable
fun colors() = listOf(
    "primary" to MaterialTheme.colors.primary,
    "primaryVariant" to MaterialTheme.colors.primaryVariant,
    "secondary" to MaterialTheme.colors.secondary,
    "secondaryVariant" to MaterialTheme.colors.secondaryVariant,
    "background" to MaterialTheme.colors.background,
    "surface" to MaterialTheme.colors.surface,
    "surfaceOver" to MaterialTheme.colors.surfaceOver,
    "error" to MaterialTheme.colors.error,
    "onPrimary" to MaterialTheme.colors.onPrimary,
    "onSecondary" to MaterialTheme.colors.onSecondary,
    "onBackground" to MaterialTheme.colors.onBackground,
    "onSurface" to MaterialTheme.colors.onSurface,
    "onError" to MaterialTheme.colors.onError,
)

@Composable
fun ColorsScreen(navController: NavController) {

    val colors = colors()

    Scaffold(topBar = {
        ToolBar(
            navigationIcon = {
                ToolbarBackButton {
                    navController.popBackStack()
                }
            },
            title = {
                Text("Colors")
            }
        )
    }) {
        LazyVerticalGrid(
            modifier = Modifier.padding(it),
            cells = GridCells.Fixed(3),
            contentPadding = PaddingValues(10.dp)
        ) {
            items(
                colors + (0..colors.size % 3).map { null }
            ) { item ->
                if (item != null) ColorGridItem(item.first, item.second)
                else Box(modifier = Modifier.fillMaxWidth())
            }

            item(span = { GridItemSpan(1) }) {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }

}

@Composable
private fun ColorGridItem(
    name: String,
    color: Color
) {

    Column(modifier = Modifier.padding(10.dp)) {
        Box(modifier = Modifier.background(color).size(100.dp, 100.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Text(name, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            "#%08x".format(color.toArgb()).uppercase(),
            fontSize = 10.sp,
            color = MaterialTheme.colors.onBackground.copy(0.5f)
        )
    }

}