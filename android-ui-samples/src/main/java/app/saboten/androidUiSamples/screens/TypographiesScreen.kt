package app.saboten.androidUiSamples.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import com.google.accompanist.insets.ui.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.bars.ToolbarBackButton

@Composable
private fun typographies() = listOf(
    "h1" to MaterialTheme.typography.h1,
    "h2" to MaterialTheme.typography.h2,
    "h3" to MaterialTheme.typography.h3,
    "h4" to MaterialTheme.typography.h4,
    "h5" to MaterialTheme.typography.h5,
    "h6" to MaterialTheme.typography.h6,
    "subtitle1" to MaterialTheme.typography.subtitle1,
    "subtitle2" to MaterialTheme.typography.subtitle2,
    "body1" to MaterialTheme.typography.body1,
    "body2" to MaterialTheme.typography.body2,
    "button" to MaterialTheme.typography.button,
    "caption" to MaterialTheme.typography.caption,
    "overline" to MaterialTheme.typography.overline,
)

@Composable
fun TypographiesScreen(navController: NavController) {
    val list = typographies()
    Scaffold(
        topBar = {
            BasicTopBar(
                title = {
                    Text("Typographies")
                },
                navigationIcon = {
                    ToolbarBackButton {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(list) { (name, typography) ->
                Text(
                    text = name,
                    style = typography,
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}