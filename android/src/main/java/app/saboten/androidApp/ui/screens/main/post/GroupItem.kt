package app.saboten.androidApp.ui.screens.main.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.saboten.androidUi.styles.SabotenColors

@Composable
fun GroupItem(text: String) {
    Box(
        modifier = Modifier
            .width(66.dp)
            .height(30.dp)
            .background(
                color = SabotenColors.grey100,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 12.sp)
    }
}
