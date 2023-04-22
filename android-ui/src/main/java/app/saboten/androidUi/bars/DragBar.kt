package app.saboten.androidUi.bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.saboten.androidUi.styles.SabotenColors

@Composable
fun DragBar() {
    Box(
        Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .size(39.dp, 2.dp)
            .background(SabotenColors.grey200)
    ) {
    }
}