package app.saboten.androidApp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
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
import com.ramcosta.composedestinations.navigation.navigate
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
    NavigationData(CategoryScreenDestination) {
        Icon(if (it) Icons.Rounded.Category else Icons.Outlined.Category, null)
    },
    NavigationData(PostScreenDestination) {
        Icon(if (it) Icons.Rounded.Add else Icons.Outlined.Add, null)
    },
    NavigationData(NotificationScreenDestination) {
        Icon(if (it) Icons.Rounded.Notifications else Icons.Outlined.Notifications, null)
    },
    NavigationData(ProfileScreenDestination) {
        Icon(if (it) Icons.Rounded.Person else Icons.Outlined.Person, null)
    },
)

@Composable
fun MainBottomNavigation(
    navController: NavController,
    destination: Destination?,
) {

    Column(
        modifier = Modifier
            .height(80.dp)
            .background(MaterialTheme.colors.surface),
    ) {
        Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.05f))
        BottomNavigation(
            modifier = Modifier.navigationBarsPadding(),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            mainNavigationBarData.forEach {
                if (it.direction == PostScreenDestination) {
                    BottomNavigationItem(
                        unselectedContentColor = MaterialTheme.colors.onSecondary,
                        selectedContentColor = MaterialTheme.colors.onSecondary,
                        icon = {
                            FloatingActionButton(
                                modifier = Modifier.size(36.dp),
                                elevation = FloatingActionButtonDefaults.elevation(
                                    0.dp,
                                    0.dp,
                                    0.dp,
                                    0.dp
                                ),
                                onClick = {
                                    navController.navigatorProvider
                                    navController.navigate(it.direction) {
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            ) {
                                it.icon(destination == it.direction)
                            }
                        },
                        selected = destination == it.direction,
                        onClick = {
                            navController.navigatorProvider
                            navController.navigate(it.direction) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                } else BottomNavigationItem(
                    unselectedContentColor = MaterialTheme.colors.onBackground.copy(0.2f),
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    icon = { it.icon(destination == it.direction) },
                    selected = destination == it.direction,
                    onClick = {
                        navController.navigatorProvider
                        navController.navigate(it.direction) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

}