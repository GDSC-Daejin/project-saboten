package app.saboten.androidUiSamples.screens

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.lists.BasicListItem
import app.saboten.androidUiSamples.UiSamplesAppRoutes
import app.saboten.androidUiSamples.UiSamplesViewModel

private data class Sample(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val route: UiSamplesAppRoutes
)

private val samples = listOf(
    Sample(Icons.Rounded.SmartButton, "Buttons", "버튼과 관련된 디자인을 모아둔 곳", UiSamplesAppRoutes.Buttons),
    Sample(Icons.Rounded.TextFormat, "TextFields", "텍스트 필드 디자인을 모아둔 곳", UiSamplesAppRoutes.TextFields),
    Sample(Icons.Rounded.TextFields, "Typographies", "텍스트 타이포그래피를 모아둔 곳", UiSamplesAppRoutes.Typographies),
    Sample(Icons.Rounded.Palette, "Colors", "색상을 모아둔 곳", UiSamplesAppRoutes.Colors),
    Sample(Icons.Rounded.List, "Lists", "리스트 디자인을 모아둔 곳", UiSamplesAppRoutes.Lists),
    Sample(Icons.Rounded.WebAsset, "Dialogs", "다이얼로그 디자인을 모아둔 곳", UiSamplesAppRoutes.Dialogs),
)

@Composable
fun HomeScreen(navController: NavController, viewModel: UiSamplesViewModel) {
    Scaffold(
        topBar = {
            BasicTopBar(title = {
                Text("\uD83C\uDF35 UI Samples")
            })
        },
        floatingActionButton = {
            FloatingActionButton({ viewModel.toggleTheme() }, modifier = Modifier.navigationBarsPadding()) {
                Icon(if (MaterialTheme.colors.isLight) Icons.Rounded.ModeNight else Icons.Rounded.WbSunny, null)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(samples) {
                BasicListItem(
                    leftSideUi = { Icon(it.icon, it.title, tint = MaterialTheme.colors.primary) },
                    title = it.title,
                    subtitle = it.subtitle,
                    onClick = { navController.navigate(it.route.route) }
                )
            }
        }
    }

}