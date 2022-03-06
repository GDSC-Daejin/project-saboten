package app.saboten.androidUi.lists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.styles.MainTheme

@Composable
fun FeedSelectItem() {

    Box(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
        /* TODO */
    }

}

@Preview(showBackground = true)
@Composable
private fun FeedSelectItemPreview() {
    MainTheme {
        FeedSelectItem()
    }
}