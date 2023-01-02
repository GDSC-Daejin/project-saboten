package app.saboten.androidApp.ui.screens.main.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.saboten.androidApp.ui.screens.main.MainTopBar
import app.saboten.androidUi.scaffolds.BasicScaffold
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun HomeScreen(
    navigator: DestinationsNavigator,
) {

    HomeScreenContent(
        navigator = navigator
    )
}

@Composable
fun HomeScreenContent(
    navigator: DestinationsNavigator,
) {

    val lazyListState = rememberLazyListState()

    val isHeaderScrolled by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemScrollOffset > 0
        }
    }

    val isLight = MaterialTheme.colors.isLight

    val isStatusBarIconColorDark by remember(isHeaderScrolled) {
        derivedStateOf {
            isLight && isHeaderScrolled
        }
    }

    val backgroundColor by animateColorAsState(targetValue = if (isHeaderScrolled) MaterialTheme.colors.surface else Color.Transparent)
    val contentColor by animateColorAsState(targetValue = if (isHeaderScrolled) MaterialTheme.colors.primary else Color.White)

    val systemUiController = rememberSystemUiController()

    LaunchedEffect(isStatusBarIconColorDark) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = isStatusBarIconColorDark
        )
    }

    DisposableEffect(true) {
        onDispose {
            systemUiController.setStatusBarColor(
                color = Color.Transparent,
                darkIcons = isLight
            )
        }
    }

    BasicScaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                state = lazyListState
            ) {

                item { HomeScreenTrendingItems() }

                item {
                    Spacer(modifier = Modifier.height(1000.dp))
                    Spacer(modifier = Modifier.height(20.dp))
                    Spacer(modifier = Modifier.height(80.dp))
                    Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
                }

            }

            MainTopBar(
                backgroundColor = backgroundColor,
                contentColor = contentColor
            )

        }
    }

}