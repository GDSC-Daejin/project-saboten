package app.saboten.androidApp.ui.screens.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.saboten.androidApp.ui.screens.NavGraphs
import app.saboten.androidApp.ui.screens.destinations.*
import app.saboten.androidApp.ui.screens.navDestination
import app.saboten.androidApp.ui.screens.startDestination
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
    NavigationData(NotificationsScreenDestination) {
        Icon(if (it) Icons.Rounded.Notifications else Icons.Outlined.Notifications, null)
    },
    NavigationData(PostScreenDestination) {
        Surface(
            modifier = Modifier.size(52.dp).padding(8.dp),
            shape = CircleShape,
            color = MaterialTheme.colors.secondary
        ) {
            Icon(
                Icons.Rounded.Add,
                null,
                tint = MaterialTheme.colors.onSurface
            )
        }
    },
    NavigationData(LikedScreenDestination) {
        Icon(if (it) Icons.Rounded.Favorite else Icons.Outlined.FavoriteBorder, null)
    },
    NavigationData(ProfileScreenDestination) {
        Icon(if (it) Icons.Rounded.Person else Icons.Outlined.Person, null)
    },
)

@Composable
fun MainBottomNavigation(
    navController: NavController
) {

    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()

    val destination = currentBackStackEntryAsState?.navDestination
        ?: NavGraphs.root.startRoute.startDestination

    AnimatedVisibility(
        destination in listOf(
            HomeScreenDestination,
            ProfileScreenDestination,
            NotificationsScreenDestination,
            LikedScreenDestination
        ),
        enter = slideInVertically { it },
        exit = slideOutVertically { it }
    ) {

        Surface(color = Color.Black) {
            BottomNavigation(
                modifier = Modifier.navigationBarsPadding(),
                backgroundColor = Color.Transparent
            ) {
                mainNavigationBarData.forEach {
                    BottomNavigationItem(
                        unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.2f),
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


}