package app.saboten.androidApp.ui.screens.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.saboten.androidApp.ui.destinations.*
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.Direction

private data class NavigationData(
    val direction: Direction,
    val icon: @Composable (Boolean) -> Unit
)

private val mainNavigationBarData = listOf(
    NavigationData(HomeScreenDestination) {
        Icon(if (it) Icons.Rounded.Home else Icons.Outlined.Home, null)
    },
    NavigationData(SearchScreenDestination) {
        Icon(if (it) Icons.Rounded.Search else Icons.Outlined.Search, null)
    },
    null,
    NavigationData(LikedScreenDestination) {
        Icon(if (it) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder, null)
    },
    NavigationData(ProfileScreenDestination) {
        Icon(if (it) Icons.Rounded.Person else Icons.Outlined.Person, null)
    },
)

@Composable
fun MainBottomNavigation(
    navController: NavController,
    destination : Destination?,
) {

    BottomAppBar(
        modifier = Modifier.height(80.dp),
        backgroundColor = if (MaterialTheme.colors.isLight) Color.White else Color.Black,
        cutoutShape = CircleShape,
    ) {
        BottomNavigation(
            modifier = Modifier.navigationBarsPadding(),
            backgroundColor = Color.Transparent
        ) {
            mainNavigationBarData.forEach {
                if (it == null) BottomNavigationItem(false, {}, {}, enabled = false)
                else BottomNavigationItem(
                    unselectedContentColor = MaterialTheme.colors.onBackground.copy(0.2f),
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    icon = { it.icon(destination == it.direction) },
                    selected = destination == it.direction,
                    onClick = {
                        navController.navigatorProvider
                        navController.navigateTo(it.direction) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

}