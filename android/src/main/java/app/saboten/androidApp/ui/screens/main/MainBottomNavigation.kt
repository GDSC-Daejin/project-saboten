package app.saboten.androidApp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.saboten.androidApp.ui.destinations.*
import app.saboten.androidUi.styles.SabotenColors
import app.saboten.androidUi.styles.SabotenColors.green500
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.navigateTo
import com.ramcosta.composedestinations.spec.Direction

private data class NavigationData(
    val direction: Direction,
    val icon: @Composable (Boolean) -> Unit,
)

private val mainNavigationBarData = listOf(
    NavigationData(HomeScreenDestination) {
        Icon(if (it) Icons.Rounded.Home else Icons.Rounded.Home, null, modifier = Modifier.size(30.dp))
    },
    NavigationData(CategoryScreenDestination) {
        Icon(if (it) Icons.Rounded.Dashboard else Icons.Rounded.Dashboard, null, modifier = Modifier.size(30.dp))
    },
    null,
    NavigationData(SearchScreenDestination) {
        Icon(if (it) Icons.Rounded.Search else Icons.Rounded.Search, null, modifier = Modifier.size(30.dp))
    },
    NavigationData(ProfileScreenDestination) {
        Icon(if (it) Icons.Rounded.Person else Icons.Rounded.Person, null, modifier = Modifier.size(30.dp))
    },
)

@Composable
fun MainBottomNavigation(
    navController: NavController,
    destination: Destination?,
) {

    Column(
        modifier = Modifier
            .background(
                MaterialTheme.colors.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ),
    ) {
        BottomNavigation(
            modifier = Modifier.navigationBarsPadding(),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            mainNavigationBarData.forEach {
                if (it == null) Spacer(modifier = Modifier.weight(1f))
                else BottomNavigationItem(
                    unselectedContentColor = SabotenColors.grey200,
                    selectedContentColor = MaterialTheme.colors.primary,
                    icon = { it.icon(destination == it.direction) },
                    selected = destination == it.direction,
                    onClick = {
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