package app.saboten.androidApp.ui.screens.main.notifications

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.bars.BasicTopBar
import app.saboten.androidUi.lists.CategoryItem
import app.saboten.androidUi.scaffolds.BasicScaffold
import app.saboten.androidUi.styles.MainTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination
fun NotificationsScreen(
    navigator: DestinationsNavigator
) {
    BasicScaffold(
        topBar = {
            BasicTopBar(title = {
                Text("Notifications")
            })
        }
    ) {
        CategoryContent()
    }
}

@Composable
private fun CategoryContent() {

    Box() {
        Column() {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "카테고리",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 30.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CategoryItem(text = "고민", icon = Icons.Default.QuestionMark, onClick = {})
                CategoryItem(text = "고민", icon = Icons.Default.QuestionMark, onClick = {})
                CategoryItem(text = "고민", icon = Icons.Default.QuestionMark, onClick = {})
                CategoryItem(text = "고민", icon = Icons.Default.QuestionMark, onClick = {})
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                CategoryItem(text = "고민", icon = Icons.Default.QuestionMark, onClick = {})
                CategoryItem(text = "고민", icon = Icons.Default.QuestionMark, onClick = {})
                CategoryItem(text = "고민", icon = Icons.Default.QuestionMark, onClick = {})
                CategoryItem(text = "고민", icon = Icons.Default.QuestionMark, onClick = {})
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryContentPreview() {
    MainTheme {
        CategoryContent()
    }
}