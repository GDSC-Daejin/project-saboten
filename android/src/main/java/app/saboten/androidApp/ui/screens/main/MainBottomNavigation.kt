package app.saboten.androidApp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.saboten.androidApp.ui.destinations.*
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.Direction

private data class NavigationData(
    val direction: Direction,
    val icon: @Composable (Boolean) -> Unit,
)

private data class  TypedNavigationData<T>(
    val direction: TypedDestination<T>,
    val icon: @Composable (Boolean) -> Unit,
)

private val mainNavigationBarData = listOf(
    NavigationData(HomeScreenDestination) {
        Icon(if (it) Icons.Rounded.Home else Icons.Rounded.Home, null, modifier = Modifier.size(30.dp))
    },
    TypedNavigationData(CategoryScreenDestination) {
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
                    unselectedContentColor = MaterialTheme.colors.onSurface.copy(0.4f),
                    selectedContentColor = MaterialTheme.colors.primary,
                    icon = {
                           when(it) {
                               is NavigationData -> it.icon(destination == it.direction)
                               is TypedNavigationData<*> -> it.icon(destination == it.direction)
                           }

                    },
                    selected = when(it) {
                        is NavigationData -> destination == it.direction
                        is TypedNavigationData<*> -> destination == it.direction
                        else -> false
                    },
                    onClick = {
                        when(it) {
                            is NavigationData -> navController.navigate(it.direction)
                            is TypedNavigationData<*> -> navController.navigate(it.direction.route)
                        }
                    }
                )
            }
        }
    }

}